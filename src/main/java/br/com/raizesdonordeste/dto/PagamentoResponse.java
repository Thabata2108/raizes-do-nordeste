package br.com.raizesdonordeste.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagamentoResponse {

    private Long id;
    private Long pedidoId;
    private BigDecimal valor;
    private String status;
    private LocalDateTime dataHora;

    public PagamentoResponse(
            Long id,
            Long pedidoId,
            BigDecimal valor,
            String status,
            LocalDateTime dataHora) {

        this.id = id;
        this.pedidoId = pedidoId;
        this.valor = valor;
        this.status = status;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}