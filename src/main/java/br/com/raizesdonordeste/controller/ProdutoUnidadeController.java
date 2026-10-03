package br.com.raizesdonordeste.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import br.com.raizesdonordeste.model.ProdutoUnidade;
import br.com.raizesdonordeste.service.ProdutoUnidadeService;

@RestController
@RequestMapping("/produtos-unidade")
public class ProdutoUnidadeController {

    private final ProdutoUnidadeService produtoUnidadeService;

    public ProdutoUnidadeController(
            ProdutoUnidadeService produtoUnidadeService) {
        this.produtoUnidadeService = produtoUnidadeService;
    }

    @PostMapping
    public ProdutoUnidade cadastrar(
            @RequestBody ProdutoUnidade produtoUnidade) {
        return produtoUnidadeService.salvar(produtoUnidade);
    }

    @GetMapping
    public List<ProdutoUnidade> listarTodos() {
        return produtoUnidadeService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProdutoUnidade buscarPorId(@PathVariable Long id) {
        return produtoUnidadeService.buscarPorId(id);
    }

    @PatchMapping("/{id}/estoque")
    public ProdutoUnidade atualizarEstoque(
            @PathVariable Long id,
            @RequestBody Integer quantidade) {

        return produtoUnidadeService.atualizarEstoque(id, quantidade);
    }
}