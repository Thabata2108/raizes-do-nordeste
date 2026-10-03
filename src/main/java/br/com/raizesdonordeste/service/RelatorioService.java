package br.com.raizesdonordeste.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.raizesdonordeste.dto.RelatorioResponse;
import br.com.raizesdonordeste.repository.ItemPedidoRepository;
import br.com.raizesdonordeste.repository.PagamentoRepository;
import br.com.raizesdonordeste.repository.PedidoRepository;

@Service
public class RelatorioService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    public RelatorioService(
            PedidoRepository pedidoRepository,
            ItemPedidoRepository itemPedidoRepository,
            PagamentoRepository pagamentoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public RelatorioResponse gerarRelatorio() {

        BigDecimal faturamentoTotal =
                pagamentoRepository.calcularFaturamentoAprovado();

        Long quantidadePedidos =
                pedidoRepository.count();

        Long clientesUnicos =
                pedidoRepository.contarClientesUnicos();

        List<Object[]> produtos =
                itemPedidoRepository.buscarProdutosMaisVendidos();

        String produtoMaisVendido = "Nenhum";
        Long quantidadeProdutoMaisVendido = 0L;

        if (!produtos.isEmpty()) {

            Object[] primeiro = produtos.get(0);

            produtoMaisVendido =
                    (String) primeiro[0];

            quantidadeProdutoMaisVendido =
                    ((Number) primeiro[1]).longValue();
        }

        return new RelatorioResponse(
                faturamentoTotal,
                quantidadePedidos,
                clientesUnicos,
                produtoMaisVendido,
                quantidadeProdutoMaisVendido);
    }
}