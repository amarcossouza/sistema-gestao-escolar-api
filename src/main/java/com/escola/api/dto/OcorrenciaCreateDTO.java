package com.escola.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class OcorrenciaCreateDTO {
    private Integer alunoId;
    private Integer turmaId;
    private Integer tipoOcorrenciaId;
    private LocalDate dataOcorrencia;
    private LocalTime horaOcorrencia;
    private String observacoes;
    private String emailProfessor;
    private String nomeProfessor;

    // Getters and Setters
    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }

    public Integer getTurmaId() { return turmaId; }
    public void setTurmaId(Integer turmaId) { this.turmaId = turmaId; }

    public Integer getTipoOcorrenciaId() { return tipoOcorrenciaId; }
    public void setTipoOcorrenciaId(Integer tipoOcorrenciaId) { this.tipoOcorrenciaId = tipoOcorrenciaId; }

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
}
