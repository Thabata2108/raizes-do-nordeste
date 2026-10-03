package br.com.raizesdonordeste.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import br.com.raizesdonordeste.model.Unidade;
import br.com.raizesdonordeste.service.UnidadeService;

import br.com.raizesdonordeste.model.ProdutoUnidade;
import br.com.raizesdonordeste.service.ProdutoUnidadeService;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;
    
    private final ProdutoUnidadeService produtoUnidadeService;

    public UnidadeController(
        UnidadeService unidadeService,
        ProdutoUnidadeService produtoUnidadeService) {

    this.unidadeService = unidadeService;
    this.produtoUnidadeService = produtoUnidadeService;
    }

    @PostMapping
    public Unidade cadastrar(@RequestBody Unidade unidade) {
        return unidadeService.salvar(unidade);
    }

    @GetMapping
    public List<Unidade> listarTodas() {
        return unidadeService.listarTodas();
    }

    @GetMapping("/{id}")
    public Unidade buscarPorId(@PathVariable Long id) {
        return unidadeService.buscarPorId(id);
    }

    @GetMapping("/{id}/produtos")
    public List<ProdutoUnidade> listarProdutosDisponiveis(@PathVariable Long id) {
        return produtoUnidadeService.listarDisponiveisPorUnidade(id);
    }
}