package com.escola.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "esc_professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    @Column(nullable = false)
    private String cargo;

    @Column(name = "sigla_materia", length = 5)
    private String siglaMateria;

    @Column(name = "nome_materia")
    private String nomeMateria;

    private Boolean ativo = true;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
