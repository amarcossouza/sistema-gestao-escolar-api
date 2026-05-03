
package com.escola.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "esc_turma")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_escola")
    private Escola escola;

    private String nome;
    private String periodo;
    @Column(name = "ano_letivo")
    private Integer anoLetivo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Escola getEscola() { return escola; }
    public void setEscola(Escola escola) { this.escola = escola; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Integer getAnoLetivo() { return anoLetivo; }
    public void setAnoLetivo(Integer anoLetivo) { this.anoLetivo = anoLetivo; }
    
    // ...existing code...
}
