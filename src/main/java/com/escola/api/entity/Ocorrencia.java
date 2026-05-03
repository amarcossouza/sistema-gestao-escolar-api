package com.escola.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "esc_ocorrencias")
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "aluno_id", nullable = false)
    private Integer alunoId;

    @Column(name = "turma_id", nullable = false)
    private Integer turmaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_ocorrencia_id", nullable = false)
    private TipoOcorrencia tipoOcorrencia;

    @Column(name = "tipo_ocorrencia", nullable = false, length = 255)
    private String tipoOcorrenciaString;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDate dataOcorrencia;

    @Column(name = "hora_ocorrencia", nullable = false)
    private LocalTime horaOcorrencia;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "email_professor", nullable = false, length = 255)
    private String emailProfessor;

    @Column(name = "nome_professor", nullable = false, length = 255)
    private String nomeProfessor;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }

    public Integer getTurmaId() { return turmaId; }
    public void setTurmaId(Integer turmaId) { this.turmaId = turmaId; }

    public TipoOcorrencia getTipoOcorrencia() { return tipoOcorrencia; }
    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) { this.tipoOcorrencia = tipoOcorrencia; }

    public String getTipoOcorrenciaString() { return tipoOcorrenciaString; }
    public void setTipoOcorrenciaString(String tipoOcorrenciaString) { this.tipoOcorrenciaString = tipoOcorrenciaString; }

    public LocalDate getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(LocalDate dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }

    public LocalTime getHoraOcorrencia() { return horaOcorrencia; }
    public void setHoraOcorrencia(LocalTime horaOcorrencia) { this.horaOcorrencia = horaOcorrencia; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getEmailProfessor() { return emailProfessor; }
    public void setEmailProfessor(String emailProfessor) { this.emailProfessor = emailProfessor; }

    public String getNomeProfessor() { return nomeProfessor; }
    public void setNomeProfessor(String nomeProfessor) { this.nomeProfessor = nomeProfessor; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
