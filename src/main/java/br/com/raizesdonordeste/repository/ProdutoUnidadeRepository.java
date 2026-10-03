package br.com.raizesdonordeste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.raizesdonordeste.model.ProdutoUnidade;

public interface ProdutoUnidadeRepository extends JpaRepository<ProdutoUnidade, Long> {

    List<ProdutoUnidade> findByUnidadeIdAndDisponivelTrue(Long unidadeId);
}