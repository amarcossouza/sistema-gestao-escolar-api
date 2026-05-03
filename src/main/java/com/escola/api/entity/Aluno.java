
package com.escola.api.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "esc_aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_turma")
    @JsonIgnoreProperties({"escola"})
    private Turma turma;

    // getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
