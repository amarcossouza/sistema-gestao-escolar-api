package com.escola.api.dto;

public class LoginResponse {
    private String email;
    private String nome;
    private String mensagem;

    public LoginResponse() {}

    public LoginResponse(String email, String nome, String mensagem) {
        this.email = email;
        this.nome = nome;
        this.mensagem = mensagem;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
