package br.com.raizesdonordeste.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.raizesdonordeste.model.ProdutoUnidade;
import br.com.raizesdonordeste.repository.ProdutoUnidadeRepository;

@Service
public class ProdutoUnidadeService {

    private final ProdutoUnidadeRepository produtoUnidadeRepository;

    public ProdutoUnidadeService(
            ProdutoUnidadeRepository produtoUnidadeRepository) {
        this.produtoUnidadeRepository = produtoUnidadeRepository;
    }

    public ProdutoUnidade salvar(ProdutoUnidade produtoUnidade) {
        return produtoUnidadeRepository.save(produtoUnidade);
    }

    public List<ProdutoUnidade> listarTodos() {
        return produtoUnidadeRepository.findAll();
    }

    public ProdutoUnidade buscarPorId(Long id) {
        return produtoUnidadeRepository.findById(id).orElse(null);
    }

    public List<ProdutoUnidade> listarDisponiveisPorUnidade(Long unidadeId) {
        return produtoUnidadeRepository
                .findByUnidadeIdAndDisponivelTrue(unidadeId);
    }

    public ProdutoUnidade atualizarEstoque(Long id, Integer quantidade) {

        ProdutoUnidade produtoUnidade = produtoUnidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Produto da unidade não encontrado"));

        if (quantidade < 0) {
            throw new RuntimeException(
                    "A quantidade do estoque não pode ser negativa");
        }

        produtoUnidade.setQuantidade(quantidade);

        if (quantidade == 0) {
            produtoUnidade.setDisponivel(false);
        } else {
            produtoUnidade.setDisponivel(true);
        }

        return produtoUnidadeRepository.save(produtoUnidade);
    }
}