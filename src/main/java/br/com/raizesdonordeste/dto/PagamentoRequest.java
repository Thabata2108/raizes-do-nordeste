package br.com.raizesdonordeste.dto;

public class PagamentoRequest {

    private Long pedidoId;

    public PagamentoRequest() {
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
}