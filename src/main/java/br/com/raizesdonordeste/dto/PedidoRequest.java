package br.com.raizesdonordeste.dto;

import java.util.List;

import br.com.raizesdonordeste.model.CanalPedido;

public class PedidoRequest {

    private Long usuarioId;
    private Long unidadeId;
    private List<ItemPedidoRequest> itens;
    private Integer pontosUtilizados;
    private CanalPedido canalPedido;

    public PedidoRequest() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public List<ItemPedidoRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }

    public Integer getPontosUtilizados() {
        return pontosUtilizados;
    }

    public void setPontosUtilizados(Integer pontosUtilizados) {
        this.pontosUtilizados = pontosUtilizados;
    }

    public CanalPedido getCanalPedido() {
        return canalPedido;
    }

    public void setCanalPedido(CanalPedido canalPedido) {
        this.canalPedido = canalPedido;
    }
}