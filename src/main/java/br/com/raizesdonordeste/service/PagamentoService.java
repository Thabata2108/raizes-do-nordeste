package br.com.raizesdonordeste.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.raizesdonordeste.dto.PagamentoRequest;
import br.com.raizesdonordeste.model.Pagamento;
import br.com.raizesdonordeste.model.Pedido;
import br.com.raizesdonordeste.model.Usuario;
import br.com.raizesdonordeste.repository.PagamentoRepository;
import br.com.raizesdonordeste.repository.PedidoRepository;
import br.com.raizesdonordeste.repository.UsuarioRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicoPagamentoExterno servicoPagamentoExterno;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository,
            ServicoPagamentoExterno servicoPagamentoExterno) {

        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicoPagamentoExterno = servicoPagamentoExterno;
    }

    @Transactional
    public Pagamento processarPagamento(PagamentoRequest request) {

        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() -> new RuntimeException(
                        "Pedido não encontrado"));

        if (pagamentoRepository.existsByPedidoId(pedido.getId())) {
            throw new RuntimeException(
                    "Este pedido já possui um pagamento");
        }

        String resultado = servicoPagamentoExterno.processar();

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setStatus(resultado);
        pagamento.setDataHora(LocalDateTime.now());

        Pagamento pagamentoSalvo =
                pagamentoRepository.save(pagamento);

        if ("APROVADO".equals(resultado)) {

            Usuario usuario = pedido.getUsuario();

            int pontosGanhos = pedido.getValorTotal()
                    .divideToIntegralValue(
                            java.math.BigDecimal.TEN)
                    .intValue();

            int pontosAtuais =
                    usuario.getPontos() == null
                            ? 0
                            : usuario.getPontos();

            usuario.setPontos(
                    pontosAtuais + pontosGanhos);

            usuarioRepository.save(usuario);
        }

        return pagamentoSalvo;
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id).orElse(null);
    }
}