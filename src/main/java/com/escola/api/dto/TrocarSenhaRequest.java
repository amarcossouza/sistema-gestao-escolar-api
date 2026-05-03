package com.escola.api.dto;

public class TrocarSenhaRequest {
    private String email;
    private String senhaAtual;
    private String senhaNova;

    public TrocarSenhaRequest() {}

    public TrocarSenhaRequest(String email, String senhaAtual, String senhaNova) {
        this.email = email;
        this.senhaAtual = senhaAtual;
        this.senhaNova = senhaNova;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }
}
