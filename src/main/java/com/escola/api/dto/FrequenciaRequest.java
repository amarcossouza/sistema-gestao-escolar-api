/**
 * Autor: Antonio Marcos de Souza Santos
 * Cargo: Developer Full Stack
 * Data: 07/05/2026
 */
package com.escola.api.dto;

import java.time.LocalDate;

public class FrequenciaRequest {

    // Suporte ao formato aninhado: { "aluno": { "id": 1 }, "turma": { "id": 2 } }
    private IdRef aluno;
    private IdRef turma;

    // Suporte ao formato plano: { "alunoId": 1, "turmaId": 2 }
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
        if (aluno != null && aluno.getId() != null) return aluno.getId();
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getTurmaId() {
        if (turma != null && turma.getId() != null) return turma.getId();
        return turmaId;
    }

    public void setTurmaId(Integer turmaId) {
        this.turmaId = turmaId;
    }

    public IdRef getAluno() { return aluno; }
    public void setAluno(IdRef aluno) { this.aluno = aluno; }

    public IdRef getTurma() { return turma; }
    public void setTurma(IdRef turma) { this.turma = turma; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static class IdRef {
        private Integer id;
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
    }
}
