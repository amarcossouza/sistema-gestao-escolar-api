package com.escola.api.service;

import com.escola.api.dto.OcorrenciaCreateDTO;
import com.escola.api.entity.Ocorrencia;
import com.escola.api.entity.TipoOcorrencia;
import com.escola.api.repository.OcorrenciaRepository;
import com.escola.api.repository.TipoOcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OcorrenciaService {
    private final OcorrenciaRepository ocorrenciaRepository;
    private final TipoOcorrenciaRepository tipoOcorrenciaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, TipoOcorrenciaRepository tipoOcorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.tipoOcorrenciaRepository = tipoOcorrenciaRepository;
    }

    public Ocorrencia salvar(Ocorrencia ocorrencia) {
        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia criarOcorrencia(OcorrenciaCreateDTO dto) {
        // Buscar o tipo de ocorrência
        Optional<TipoOcorrencia> tipoOcorrencia = tipoOcorrenciaRepository.findById(dto.getTipoOcorrenciaId());
        if (tipoOcorrencia.isEmpty()) {
            throw new IllegalArgumentException("Tipo de ocorrência não encontrado");
        }

        // Criar a ocorrência
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setAlunoId(dto.getAlunoId());
        ocorrencia.setTurmaId(dto.getTurmaId());
        ocorrencia.setTipoOcorrencia(tipoOcorrencia.get());
        ocorrencia.setTipoOcorrenciaString(tipoOcorrencia.get().getNome()); // Preenche o campo STRING
        ocorrencia.setDataOcorrencia(dto.getDataOcorrencia());
        ocorrencia.setHoraOcorrencia(dto.getHoraOcorrencia());
        ocorrencia.setObservacoes(dto.getObservacoes());
        ocorrencia.setEmailProfessor(dto.getEmailProfessor());
        ocorrencia.setNomeProfessor(dto.getNomeProfessor());

        return ocorrenciaRepository.save(ocorrencia);
    }

    public List<Ocorrencia> buscarPorAluno(Integer alunoId) {
        return ocorrenciaRepository.findByAlunoIdOrderByDataOcorrenciaDescHoraOcorrenciaDesc(alunoId);
    }

    public List<Ocorrencia> buscarPorAlunoETurma(Integer alunoId, Integer turmaId) {
        return ocorrenciaRepository.findByAlunoIdAndTurmaIdOrderByDataOcorrenciaDescHoraOcorrenciaDesc(alunoId, turmaId);
    }

    public List<Ocorrencia> buscarTodas() {
        return ocorrenciaRepository.findAll();
    }

    public Ocorrencia buscarPorId(Integer id) {
        return ocorrenciaRepository.findById(id).orElse(null);
    }

    public void deletar(Integer id) {
        ocorrenciaRepository.deleteById(id);
    }
}
