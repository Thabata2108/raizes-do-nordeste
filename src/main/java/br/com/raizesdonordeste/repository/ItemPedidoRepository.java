package br.com.raizesdonordeste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.raizesdonordeste.model.ItemPedido;

public interface ItemPedidoRepository
        extends JpaRepository<ItemPedido, Long> {

    @Query("""
        SELECT i.produtoUnidade.produto.nome,
               SUM(i.quantidade)
        FROM ItemPedido i
        WHERE EXISTS (
            SELECT p.id
            FROM Pagamento p
            WHERE p.pedido.id = i.pedido.id
              AND p.status = 'APROVADO'
        )
        GROUP BY i.produtoUnidade.produto.id,
                 i.produtoUnidade.produto.nome
        ORDER BY SUM(i.quantidade) DESC
        """)
    List<Object[]> buscarProdutosMaisVendidos();
}