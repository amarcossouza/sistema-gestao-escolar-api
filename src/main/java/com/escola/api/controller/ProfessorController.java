package com.escola.api.controller;

import com.escola.api.dto.ProfessorDTO;
import com.escola.api.entity.Professor;
import com.escola.api.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<List<Professor>> listarTodos() {
        return ResponseEntity.ok(professorService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Professor>> listarAtivos() {
        return ResponseEntity.ok(professorService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Professor> professor = professorService.buscarPorId(id);
        if (professor.isEmpty()) {
            return ResponseEntity.status(404).body("Professor não encontrado");
        }
        return ResponseEntity.ok(professor.get());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ProfessorDTO dto) {
        Object result = professorService.cadastrar(dto);
        if (result instanceof String) {
            return ResponseEntity.status(409).body(result);
        }
        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ProfessorDTO dto) {
        Object result = professorService.atualizar(id, dto);
        if (result instanceof String) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        String result = professorService.excluir(id);
        if ("Professor excluído com sucesso".equals(result)) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(404).body(result);
    }
}
