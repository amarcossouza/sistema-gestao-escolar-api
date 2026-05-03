package com.escola.api.dto;

/**
 * DTO para requisição de geração de relatório PDF de frequência
 */
public class FrequenciaRelatorioPdfRequest {
    private Integer turmaId;
    private Integer mes;
    private Integer ano;

    // Construtores
    public FrequenciaRelatorioPdfRequest() {
    }

    public FrequenciaRelatorioPdfRequest(Integer turmaId, Integer mes, Integer ano) {
        this.turmaId = turmaId;
        this.mes = mes;
        this.ano = ano;
    }

    // Getters e Setters
    public Integer getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Integer turmaId) {
        this.turmaId = turmaId;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "FrequenciaRelatorioPdfRequest{" +
                "turmaId=" + turmaId +
                ", mes=" + mes +
                ", ano=" + ano +
                '}';
    }
}
