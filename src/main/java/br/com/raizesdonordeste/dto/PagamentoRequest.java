package br.com.raizesdonordeste.dto;

public class PagamentoRequest {

    private Long pedidoId;
    private String resultadoSimulado;

    public PagamentoRequest() {
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getResultadoSimulado() {
        return resultadoSimulado;
    }

    public void setResultadoSimulado(String resultadoSimulado) {
        this.resultadoSimulado = resultadoSimulado;
    }
}