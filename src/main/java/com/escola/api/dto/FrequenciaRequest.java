package com.escola.api.dto;

import java.time.LocalDate;

public class FrequenciaRequest {
    private Integer alunoId;
    private Integer turmaId;
    private LocalDate data;
    private String status;

    public FrequenciaRequest() {}

    public FrequenciaRequest(Integer alunoId, Integer turmaId, LocalDate data, String status) {
        this.alunoId = alunoId;
        this.turmaId = turmaId;
        this.data = data;
        this.status = status;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Integer turmaId) {
        this.turmaId = turmaId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
