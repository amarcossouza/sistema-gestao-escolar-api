package com.escola.api.dto;

import com.escola.api.entity.Aluno;
import com.escola.api.entity.ChamadaConfirmada;
import com.escola.api.entity.Frequencia;
import java.util.List;
import java.util.Map;

public class FrequenciaCompleteResponseDTO {
    private List<Aluno> alunos;
    private List<Frequencia> frequencias;
    private Map<Integer, Integer> ocorrenciasCount; // alunoId -> count
    private List<ChamadaConfirmada> chamadasConfirmadas; // NOVO: chamadas confirmadas do período

    public FrequenciaCompleteResponseDTO() {
    }

    public FrequenciaCompleteResponseDTO(List<Aluno> alunos, List<Frequencia> frequencias, Map<Integer, Integer> ocorrenciasCount) {
        this.alunos = alunos;
        this.frequencias = frequencias;
        this.ocorrenciasCount = ocorrenciasCount;
        this.chamadasConfirmadas = List.of(); // Vazio por padrão
    }

    public FrequenciaCompleteResponseDTO(List<Aluno> alunos, List<Frequencia> frequencias, Map<Integer, Integer> ocorrenciasCount, List<ChamadaConfirmada> chamadasConfirmadas) {
        this.alunos = alunos;
        this.frequencias = frequencias;
        this.ocorrenciasCount = ocorrenciasCount;
        this.chamadasConfirmadas = chamadasConfirmadas;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Frequencia> getFrequencias() {
        return frequencias;
    }

    public void setFrequencias(List<Frequencia> frequencias) {
        this.frequencias = frequencias;
    }

    public Map<Integer, Integer> getOcorrenciasCount() {
        return ocorrenciasCount;
    }

    public void setOcorrenciasCount(Map<Integer, Integer> ocorrenciasCount) {
        this.ocorrenciasCount = ocorrenciasCount;
    }

    public List<ChamadaConfirmada> getChamadasConfirmadas() {
        return chamadasConfirmadas;
    }

    public void setChamadasConfirmadas(List<ChamadaConfirmada> chamadasConfirmadas) {
        this.chamadasConfirmadas = chamadasConfirmadas;
    }
}
