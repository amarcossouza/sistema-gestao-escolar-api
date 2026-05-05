package com.escola.api.service;

import com.escola.api.dto.ProfessorDTO;
import com.escola.api.entity.Professor;
import com.escola.api.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public List<Professor> listarAtivos() {
        return professorRepository.findByAtivoTrue();
    }

    public Optional<Professor> buscarPorId(Integer id) {
        return professorRepository.findById(id);
    }

    public Object cadastrar(ProfessorDTO dto) {
        if (professorRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "Email já cadastrado";
        }
        Professor professor = new Professor();
        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setTelefone(dto.getTelefone());
        professor.setCargo(dto.getCargo() != null ? dto.getCargo().toUpperCase() : "PROFESSOR");
        professor.setSiglaMateria(dto.getSiglaMateria() != null ? dto.getSiglaMateria().toUpperCase() : null);
        professor.setNomeMateria(dto.getNomeMateria());
        professor.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        professor.setCriadoEm(LocalDateTime.now());
        return professorRepository.save(professor);
    }

    public Object atualizar(Integer id, ProfessorDTO dto) {
        Optional<Professor> opt = professorRepository.findById(id);
        if (opt.isEmpty()) {
            return "Professor não encontrado";
        }
        Professor professor = opt.get();
        if (dto.getNome() != null) professor.setNome(dto.getNome());
        if (dto.getEmail() != null) professor.setEmail(dto.getEmail());
        if (dto.getTelefone() != null) professor.setTelefone(dto.getTelefone());
        if (dto.getCargo() != null) professor.setCargo(dto.getCargo().toUpperCase());
        if (dto.getSiglaMateria() != null) professor.setSiglaMateria(dto.getSiglaMateria().toUpperCase());
        if (dto.getNomeMateria() != null) professor.setNomeMateria(dto.getNomeMateria());
        if (dto.getAtivo() != null) professor.setAtivo(dto.getAtivo());
        professor.setAtualizadoEm(LocalDateTime.now());
        return professorRepository.save(professor);
    }

    public String excluir(Integer id) {
        if (professorRepository.findById(id).isEmpty()) {
            return "Professor não encontrado";
        }
        professorRepository.deleteById(id);
        return "Professor excluído com sucesso";
    }
}
