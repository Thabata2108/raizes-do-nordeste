package br.com.raizesdonordeste.dto;

public class ItemPedidoRequest {

    private Long produtoUnidadeId;
    private Integer quantidade;

    public ItemPedidoRequest() {
    }

    public Long getProdutoUnidadeId() {
        return produtoUnidadeId;
    }

    public void setProdutoUnidadeId(Long produtoUnidadeId) {
        this.produtoUnidadeId = produtoUnidadeId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}