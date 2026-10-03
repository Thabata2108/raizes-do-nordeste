package br.com.raizesdonordeste.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.raizesdonordeste.dto.PagamentoRequest;
import br.com.raizesdonordeste.dto.PagamentoResponse;
import br.com.raizesdonordeste.model.Pagamento;
import br.com.raizesdonordeste.model.Pedido;
import br.com.raizesdonordeste.service.PagamentoService;
import br.com.raizesdonordeste.service.PedidoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PedidoService pedidoService;

    public PagamentoController(
            PagamentoService pagamentoService,
            PedidoService pedidoService) {

        this.pagamentoService = pagamentoService;
        this.pedidoService = pedidoService;
    }

    private PagamentoResponse converterParaResponse(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getPedido().getId(),
                pagamento.getValor(),
                pagamento.getStatus(),
                pagamento.getDataHora()
        );
    }

    @PostMapping
    public PagamentoResponse processarPagamento(
            @RequestBody PagamentoRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        Pedido pedido =
                pedidoService.buscarPorId(request.getPedidoId());

        if (pedido == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pedido não encontrado");
        }

        Long usuarioIdToken =
                jwt.getClaim("usuarioId");

        String perfil =
                jwt.getClaimAsString("perfil");

        boolean cliente =
                "CLIENTE".equals(perfil);

        boolean proprioPedido =
                pedido.getUsuario().getId()
                        .equals(usuarioIdToken);

        if (cliente && !proprioPedido) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Cliente não autorizado a pagar este pedido");
        }

        Pagamento pagamento =
                pagamentoService.processarPagamento(request);

        return converterParaResponse(pagamento);
    }

    @GetMapping
    public List<PagamentoResponse> listarTodos() {
        return pagamentoService.listarTodos()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PagamentoResponse buscarPorId(@PathVariable Long id) {

        Pagamento pagamento =
                pagamentoService.buscarPorId(id);

        if (pagamento == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pagamento não encontrado");
        }

        return converterParaResponse(pagamento);
    }
}