package br.com.raizesdonordeste.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.raizesdonordeste.model.Pagamento;

public interface PagamentoRepository
        extends JpaRepository<Pagamento, Long> {

    boolean existsByPedidoId(Long pedidoId);

    @Query("""
        SELECT COALESCE(SUM(p.valor), 0)
        FROM Pagamento p
        WHERE p.status = 'APROVADO'
        """)
    BigDecimal calcularFaturamentoAprovado();
}