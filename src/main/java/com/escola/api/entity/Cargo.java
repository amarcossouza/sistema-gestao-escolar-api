package com.escola.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "esc_cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String valor;

    @Column(nullable = false)
    private String descricao;

    private Boolean ativo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
