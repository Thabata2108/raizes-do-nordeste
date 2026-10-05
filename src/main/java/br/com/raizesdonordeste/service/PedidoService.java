package br.com.raizesdonordeste.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.raizesdonordeste.dto.ItemPedidoRequest;
import br.com.raizesdonordeste.dto.PedidoRequest;
import br.com.raizesdonordeste.model.CanalPedido;
import br.com.raizesdonordeste.model.ItemPedido;
import br.com.raizesdonordeste.model.Pedido;
import br.com.raizesdonordeste.model.ProdutoUnidade;
import br.com.raizesdonordeste.model.Unidade;
import br.com.raizesdonordeste.model.Usuario;
import br.com.raizesdonordeste.repository.ItemPedidoRepository;
import br.com.raizesdonordeste.repository.PedidoRepository;
import br.com.raizesdonordeste.repository.ProdutoUnidadeRepository;
import br.com.raizesdonordeste.repository.UnidadeRepository;
import br.com.raizesdonordeste.repository.UsuarioRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoUnidadeRepository produtoUnidadeRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ItemPedidoRepository itemPedidoRepository,
            UsuarioRepository usuarioRepository,
            UnidadeRepository unidadeRepository,
            ProdutoUnidadeRepository produtoUnidadeRepository) {

        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
        this.produtoUnidadeRepository = produtoUnidadeRepository;
    }

    @Transactional
    public Pedido criarPedido(PedidoRequest request) {

        if (request.getCanalPedido() == null) {
            throw new RuntimeException(
                    "O canal do pedido é obrigatório");
        }

        if (request.getItens() == null || request.getItens().isEmpty()) {
            throw new RuntimeException(
                    "O pedido deve possuir pelo menos um item");
        }

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException(
                        "Usuário não encontrado"));

        Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
                .orElseThrow(() -> new RuntimeException(
                        "Unidade não encontrada"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setUnidade(unidade);
        pedido.setCanalPedido(request.getCanalPedido());
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus("Recebido");
        pedido.setValorTotal(BigDecimal.ZERO);

        pedido = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequest itemRequest : request.getItens()) {

            if (itemRequest.getQuantidade() == null
                    || itemRequest.getQuantidade() <= 0) {

                throw new RuntimeException(
                        "A quantidade do item deve ser maior que zero");
            }

            ProdutoUnidade produtoUnidade = produtoUnidadeRepository
                    .findById(itemRequest.getProdutoUnidadeId())
                    .orElseThrow(() -> new RuntimeException(
                            "Produto não encontrado"));

            if (!produtoUnidade.getUnidade().getId()
                    .equals(unidade.getId())) {

                throw new RuntimeException(
                        "Produto não pertence à unidade escolhida");
            }

            if (!Boolean.TRUE.equals(produtoUnidade.getDisponivel())) {
                throw new RuntimeException(
                        "Produto indisponível");
            }

            if (produtoUnidade.getQuantidade()
                    < itemRequest.getQuantidade()) {

                throw new RuntimeException(
                        "Estoque insuficiente");
            }

            BigDecimal subtotal = produtoUnidade.getPreco()
                    .multiply(BigDecimal.valueOf(
                            itemRequest.getQuantidade()));

            total = total.add(subtotal);

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProdutoUnidade(produtoUnidade);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setValorUnitario(produtoUnidade.getPreco());

            itemPedidoRepository.save(item);

            produtoUnidade.setQuantidade(
                    produtoUnidade.getQuantidade()
                            - itemRequest.getQuantidade());

            if (produtoUnidade.getQuantidade() == 0) {
                produtoUnidade.setDisponivel(false);
            }

            produtoUnidadeRepository.save(produtoUnidade);
        }

        int pontosUtilizados =
                request.getPontosUtilizados() == null
                        ? 0
                        : request.getPontosUtilizados();

        if (pontosUtilizados < 0) {
            throw new RuntimeException(
                    "A quantidade de pontos não pode ser negativa");
        }

        int pontosDisponiveis =
                usuario.getPontos() == null
                        ? 0
                        : usuario.getPontos();

        if (pontosUtilizados > pontosDisponiveis) {
            throw new RuntimeException(
                    "Pontos insuficientes");
        }

        BigDecimal desconto = BigDecimal.valueOf(pontosUtilizados)
                .divide(BigDecimal.TEN);

        if (desconto.compareTo(total) > 0) {
            throw new RuntimeException(
                    "O desconto não pode ser maior que o valor do pedido");
        }

        total = total.subtract(desconto);

        if (pontosUtilizados > 0) {
            usuario.setPontos(
                    pontosDisponiveis - pontosUtilizados);

            usuarioRepository.save(usuario);
        }

        pedido.setValorTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos(CanalPedido canalPedido) {

        if (canalPedido != null) {
            return pedidoRepository.findByCanalPedido(canalPedido);
        }

        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido atualizarStatus(Long id, String status) {

        List<String> statusPermitidos = List.of(
                "Recebido",
                "Em preparo",
                "Pronto",
                "Finalizado",
                "Cancelado");

        if (status == null || !statusPermitidos.contains(status)) {
            throw new RuntimeException(
                    "Status inválido");
        }

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Pedido não encontrado"));

        pedido.setStatus(status);

        return pedidoRepository.save(pedido);
    }
}