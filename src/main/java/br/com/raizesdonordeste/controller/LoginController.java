package br.com.raizesdonordeste.controller;

import org.springframework.web.bind.annotation.*;

import br.com.raizesdonordeste.dto.LoginRequest;
import br.com.raizesdonordeste.dto.LoginResponse;
import br.com.raizesdonordeste.model.Usuario;
import br.com.raizesdonordeste.service.TokenService;
import br.com.raizesdonordeste.service.UsuarioService;

@RestController
public class LoginController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public LoginController(
            UsuarioService usuarioService,
            TokenService tokenService) {

        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.autenticar(
                request.getEmail(),
                request.getSenha());

        String token = tokenService.gerarToken(usuario);

        return new LoginResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getPerfil(),
                token,
                "Login realizado com sucesso");
    }
}