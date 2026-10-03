package br.com.raizesdonordeste.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoResponse {

    private Long id;
    private LocalDateTime dataHora;
    private String status;
    private BigDecimal valorTotal;
    private String unidade;

    public PedidoResponse(
            Long id,
            LocalDateTime dataHora,
            String status,
            BigDecimal valorTotal,
            String unidade) {

        this.id = id;
        this.dataHora = dataHora;
        this.status = status;
        this.valorTotal = valorTotal;
        this.unidade = unidade;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public String getUnidade() {
        return unidade;
    }
}