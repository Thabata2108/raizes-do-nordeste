package br.com.raizesdonordeste.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.raizesdonordeste.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT COALESCE(SUM(p.valorTotal), 0) FROM Pedido p")
    BigDecimal calcularFaturamentoTotal();

    @Query("SELECT COUNT(DISTINCT p.usuario.id) FROM Pedido p")
    Long contarClientesUnicos();
}