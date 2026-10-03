package br.com.raizesdonordeste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.raizesdonordeste.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}