package br.com.raizesdonordeste.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.raizesdonordeste.dto.PedidoRequest;
import br.com.raizesdonordeste.dto.PedidoResponse;
import br.com.raizesdonordeste.model.Pedido;
import br.com.raizesdonordeste.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    private PedidoResponse converterParaResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getUnidade().getNome()
        );
    }

    @PostMapping
    public PedidoResponse criarPedido(
            @RequestBody PedidoRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        Long usuarioIdToken =
                jwt.getClaim("usuarioId");

        String perfil =
                jwt.getClaimAsString("perfil");

        boolean cliente =
                "CLIENTE".equals(perfil);

        if (cliente &&
                !usuarioIdToken.equals(request.getUsuarioId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Cliente não pode criar pedido para outro usuário");
        }

        Pedido pedido =
                pedidoService.criarPedido(request);

        return converterParaResponse(pedido);
    }

    @GetMapping
    public List<PedidoResponse> listarTodos() {
        return pedidoService.listarTodos()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Pedido pedido = pedidoService.buscarPorId(id);

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
                    "Cliente não autorizado a consultar este pedido");
        }

        return converterParaResponse(pedido);
    }

    @PatchMapping("/{id}/status")
    public PedidoResponse atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> dados) {

        Pedido pedido = pedidoService.atualizarStatus(
                id,
                dados.get("status"));

        return converterParaResponse(pedido);
    }
}