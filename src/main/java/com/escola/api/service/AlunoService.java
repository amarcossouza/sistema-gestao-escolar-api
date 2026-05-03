package com.escola.api.service;

import com.escola.api.entity.Aluno;
// import removido: com.escola.api.entity.Turma
import com.escola.api.repository.AlunoRepository;
// import removido: com.escola.api.repository.TurmaRepository
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    // campo removido: turmaRepository

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Integer id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public void deletar(Integer id) {
        alunoRepository.deleteById(id);
    }

    public List<Aluno> buscarAlunosPorTurma(Integer turmaId) {
        return alunoRepository.findByTurmaId(turmaId);
    }
}
