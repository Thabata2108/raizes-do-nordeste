package br.com.raizesdonordeste.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.raizesdonordeste.dto.UsuarioResponse;
import br.com.raizesdonordeste.model.Usuario;
import br.com.raizesdonordeste.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private UsuarioResponse converterParaResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getPontos()
        );
    }

    @PostMapping
    public UsuarioResponse cadastrar(@RequestBody Usuario usuario) {
        usuario.setPerfil("CLIENTE");
        usuario.setPontos(0);

        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        return converterParaResponse(usuarioSalvo);
    }

    @GetMapping
    public List<UsuarioResponse> listarTodos() {
        return usuarioService.listarTodos()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            return null;
        }

        return converterParaResponse(usuario);
    }

    @GetMapping("/{id}/pontos")
    public Integer consultarPontos(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Long usuarioIdToken =
                jwt.getClaim("usuarioId");

        String perfil =
                jwt.getClaimAsString("perfil");

        boolean administrador =
                "ADMINISTRADOR".equals(perfil);

        boolean proprioUsuario =
                id.equals(usuarioIdToken);

        if (!administrador && !proprioUsuario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Usuário não autorizado a consultar estes pontos");
        }

        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado");
        }

        return usuario.getPontos();
    }
}