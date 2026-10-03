package br.com.raizesdonordeste.dto;

import java.math.BigDecimal;

public class RelatorioResponse {

    private BigDecimal faturamentoTotal;
    private Long quantidadePedidos;
    private Long clientesUnicos;
    private String produtoMaisVendido;
    private Long quantidadeProdutoMaisVendido;

    public RelatorioResponse(
            BigDecimal faturamentoTotal,
            Long quantidadePedidos,
            Long clientesUnicos,
            String produtoMaisVendido,
            Long quantidadeProdutoMaisVendido) {

        this.faturamentoTotal = faturamentoTotal;
        this.quantidadePedidos = quantidadePedidos;
        this.clientesUnicos = clientesUnicos;
        this.produtoMaisVendido = produtoMaisVendido;
        this.quantidadeProdutoMaisVendido = quantidadeProdutoMaisVendido;
    }

    public BigDecimal getFaturamentoTotal() {
        return faturamentoTotal;
    }

    public Long getQuantidadePedidos() {
        return quantidadePedidos;
    }

    public Long getClientesUnicos() {
        return clientesUnicos;
    }

    public String getProdutoMaisVendido() {
        return produtoMaisVendido;
    }

    public Long getQuantidadeProdutoMaisVendido() {
        return quantidadeProdutoMaisVendido;
    }
}