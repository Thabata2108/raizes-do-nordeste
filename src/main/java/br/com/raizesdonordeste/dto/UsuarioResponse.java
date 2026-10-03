package br.com.raizesdonordeste.dto;

public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String perfil;
    private Integer pontos;

    public UsuarioResponse(
            Long id,
            String nome,
            String email,
            String perfil,
            Integer pontos) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.pontos = pontos;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPerfil() {
        return perfil;
    }

    public Integer getPontos() {
        return pontos;
    }
}