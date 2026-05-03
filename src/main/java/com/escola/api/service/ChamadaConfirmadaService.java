package com.escola.api.service;

import com.escola.api.entity.ChamadaConfirmada;
import com.escola.api.repository.ChamadaConfirmadaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadaConfirmadaService {
    private final ChamadaConfirmadaRepository chamadaRepository;

    public ChamadaConfirmadaService(ChamadaConfirmadaRepository chamadaRepository) {
        this.chamadaRepository = chamadaRepository;
    }

    public ChamadaConfirmada salvar(ChamadaConfirmada chamada) {
        return chamadaRepository.save(chamada);
    }

    public List<ChamadaConfirmada> buscarPorTurmaEPeriodo(Integer turmaId, LocalDate dataInicio, LocalDate dataFim) {
        return chamadaRepository.findByTurmaIdAndDataChamadaBetween(turmaId, dataInicio, dataFim);
    }

    public ChamadaConfirmada confirmarChamada(Integer turmaId, LocalDate dataChamada, LocalDateTime dataHoraConfirmacao, String emailProfessor) {
        // Verifica se já existe confirmação para esse dia
        ChamadaConfirmada existente = chamadaRepository.findByTurmaIdAndDataChamada(turmaId, dataChamada);
        
        if (existente != null) {
            // Atualiza a confirmação existente
            existente.setDataHoraConfirmacao(dataHoraConfirmacao);
            existente.setEmailProfessor(emailProfessor);
            return chamadaRepository.save(existente);
        } else {
            // Cria nova confirmação
            ChamadaConfirmada nova = new ChamadaConfirmada();
            nova.setTurmaId(turmaId);
            nova.setDataChamada(dataChamada);
            nova.setDataHoraConfirmacao(dataHoraConfirmacao);
            nova.setEmailProfessor(emailProfessor);
            return chamadaRepository.save(nova);
        }
    }

    public void removerConfirmacao(Integer turmaId, LocalDate dataChamada) {
        ChamadaConfirmada existente = chamadaRepository.findByTurmaIdAndDataChamada(turmaId, dataChamada);
        if (existente != null) {
            chamadaRepository.delete(existente);
        }
    }
}
