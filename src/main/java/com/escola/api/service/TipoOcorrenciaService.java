package com.escola.api.service;

import com.escola.api.entity.TipoOcorrencia;
import com.escola.api.repository.TipoOcorrenciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TipoOcorrenciaService {
    private final TipoOcorrenciaRepository repository;

    public TipoOcorrenciaService(TipoOcorrenciaRepository repository) {
        this.repository = repository;
    }

    public List<TipoOcorrencia> listarTodos() {
        return repository.findAll();
    }

    public List<TipoOcorrencia> listarAtivos() {
        return repository.findAllAtivos();
    }

    public Optional<TipoOcorrencia> obterPorId(Integer id) {
        return repository.findById(id);
    }

    public TipoOcorrencia criar(TipoOcorrencia tipoOcorrencia) {
        // Verificar se já existe um tipo com o mesmo nome
        TipoOcorrencia existente = repository.findByNome(tipoOcorrencia.getNome());
        if (existente != null) {
            throw new IllegalArgumentException("Já existe um tipo de ocorrência com esse nome");
        }
        return repository.save(tipoOcorrencia);
    }

    public TipoOcorrencia atualizar(Integer id, TipoOcorrencia tipoOcorrencia) {
        Optional<TipoOcorrencia> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Tipo de ocorrência não encontrado");
        }

        TipoOcorrencia atual = existente.get();
        
        // Verificar se o novo nome já está sendo usado por outro registro
        if (!atual.getNome().equals(tipoOcorrencia.getNome())) {
            TipoOcorrencia comMesmoNome = repository.findByNome(tipoOcorrencia.getNome());
            if (comMesmoNome != null) {
                throw new IllegalArgumentException("Já existe um tipo de ocorrência com esse nome");
            }
        }

        atual.setNome(tipoOcorrencia.getNome());
        atual.setDescricao(tipoOcorrencia.getDescricao());
        atual.setAtivo(tipoOcorrencia.getAtivo());

        return repository.save(atual);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Tipo de ocorrência não encontrado");
        }
        repository.deleteById(id);
    }

    public void desativar(Integer id) {
        Optional<TipoOcorrencia> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Tipo de ocorrência não encontrado");
        }
        
        TipoOcorrencia tipoOcorrencia = existente.get();
        tipoOcorrencia.setAtivo(false);
        repository.save(tipoOcorrencia);
    }
}
