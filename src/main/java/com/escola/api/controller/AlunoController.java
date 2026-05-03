package com.escola.api.controller;

import com.escola.api.dto.AlunoDTO;
import com.escola.api.entity.Aluno;
import com.escola.api.repository.AlunoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AlunoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(AlunoDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public AlunoDTO salvar(@RequestBody Aluno aluno) {
        Aluno saved = repository.save(aluno);
        return new AlunoDTO(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Aluno> alunoOpt = repository.findById(id);
        if (alunoOpt.isPresent()) {
            return ResponseEntity.ok(new AlunoDTO(alunoOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizar(@PathVariable Integer id, @RequestBody Aluno dados) {
        Optional<Aluno> alunoOpt = repository.findById(id);
        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            aluno.setNome(dados.getNome());
            if (dados.getTurma() != null) {
                aluno.setTurma(dados.getTurma());
            }
            Aluno atualizado = repository.save(aluno);
            return ResponseEntity.ok(new AlunoDTO(atualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Optional<Aluno> alunoOpt = repository.findById(id);
        if (alunoOpt.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
