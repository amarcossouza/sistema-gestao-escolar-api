package com.escola.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "esc_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    private Boolean ativo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "tentativas_login")
    private Integer tentativasLogin;

    @Column(name = "senha_temporaria")
    private Boolean senhaTemporaria;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }

    public Integer getTentativasLogin() { return tentativasLogin; }
    public void setTentativasLogin(Integer tentativasLogin) { this.tentativasLogin = tentativasLogin; }

    public Boolean getSenhaTemporaria() { return senhaTemporaria; }
    public void setSenhaTemporaria(Boolean senhaTemporaria) { this.senhaTemporaria = senhaTemporaria; }
}
