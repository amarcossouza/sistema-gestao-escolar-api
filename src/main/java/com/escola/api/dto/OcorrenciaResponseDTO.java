package com.escola.api.dto;

import com.escola.api.entity.Ocorrencia;
import com.escola.api.entity.TipoOcorrencia;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OcorrenciaResponseDTO {
    private Integer id;
    private Integer alunoId;
    private Integer turmaId;
    private TipoOcorrencia tipoOcorrencia;
    private String tipoOcorrenciaString; // Para compatibilidade com dados antigos
    private LocalDate dataOcorrencia;
    private LocalTime horaOcorrencia;
    private String observacoes;
    private String emailProfessor;
    private String nomeProfessor;
    private LocalDateTime criadoEm;

    public OcorrenciaResponseDTO(Ocorrencia ocorrencia) {
        this.id = ocorrencia.getId();
        this.alunoId = ocorrencia.getAlunoId();
        this.turmaId = ocorrencia.getTurmaId();
        this.tipoOcorrencia = ocorrencia.getTipoOcorrencia();
        this.dataOcorrencia = ocorrencia.getDataOcorrencia();
        this.horaOcorrencia = ocorrencia.getHoraOcorrencia();
        this.observacoes = ocorrencia.getObservacoes();
        this.emailProfessor = ocorrencia.getEmailProfessor();
        this.nomeProfessor = ocorrencia.getNomeProfessor();
        this.criadoEm = ocorrencia.getCriadoEm();
    }

    // Getters
    public Integer getId() { return id; }
    public Integer getAlunoId() { return alunoId; }
    public Integer getTurmaId() { return turmaId; }
    public TipoOcorrencia getTipoOcorrencia() { return tipoOcorrencia; }
    public String getTipoOcorrenciaString() { return tipoOcorrenciaString; }
    public LocalDate getDataOcorrencia() { return dataOcorrencia; }
    public LocalTime getHoraOcorrencia() { return horaOcorrencia; }
    public String getObservacoes() { return observacoes; }
    public String getEmailProfessor() { return emailProfessor; }
    public String getNomeProfessor() { return nomeProfessor; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
