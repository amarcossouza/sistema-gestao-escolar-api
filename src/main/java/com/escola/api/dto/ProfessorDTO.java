package com.escola.api.dto;

public class ProfessorDTO {

    private String nome;
    private String email;
    private String telefone;
    private String cargo;
    private String siglaMateria;
    private String nomeMateria;
    private Boolean ativo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getSiglaMateria() { return siglaMateria; }
    public void setSiglaMateria(String siglaMateria) { this.siglaMateria = siglaMateria; }

    public String getNomeMateria() { return nomeMateria; }
    public void setNomeMateria(String nomeMateria) { this.nomeMateria = nomeMateria; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
