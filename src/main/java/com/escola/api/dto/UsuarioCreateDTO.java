package com.escola.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para cadastro de novo usuário")
public class UsuarioCreateDTO {

    @Schema(description = "Email do usuário", example = "admin@admin.com.br")
    private String email;

    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

    @Schema(description = "Status ativo/inativo", example = "true")
    private Boolean ativo;

    @Schema(description = "Tentativas de login", example = "0")
    private Integer tentativasLogin;

    @Schema(description = "Senha temporária", example = "false")
    private Boolean senhaTemporaria;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getTentativasLogin() {
        return tentativasLogin;
    }

    public void setTentativasLogin(Integer tentativasLogin) {
        this.tentativasLogin = tentativasLogin;
    }

    public Boolean getSenhaTemporaria() {
        return senhaTemporaria;
    }

    public void setSenhaTemporaria(Boolean senhaTemporaria) {
        this.senhaTemporaria = senhaTemporaria;
    }
}
