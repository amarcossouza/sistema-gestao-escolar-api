package com.escola.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "esc_frequencia", uniqueConstraints = @UniqueConstraint(columnNames = {"id_aluno", "id_turma", "data"}))
public class Frequencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;

    @Column(nullable = false)
    private LocalDate data;

    @Column(length = 1, nullable = false)
    private String status;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
