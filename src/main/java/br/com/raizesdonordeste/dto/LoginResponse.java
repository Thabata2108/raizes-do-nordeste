package br.com.raizesdonordeste.dto;

public class LoginResponse {

    private Long id;
    private String nome;
    private String perfil;
    private String token;
    private String mensagem;

    public LoginResponse(
            Long id,
            String nome,
            String perfil,
            String token,
            String mensagem) {

        this.id = id;
        this.nome = nome;
        this.perfil = perfil;
        this.token = token;
        this.mensagem = mensagem;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getToken() {
        return token;
    }

    public String getMensagem() {
        return mensagem;
    }
}