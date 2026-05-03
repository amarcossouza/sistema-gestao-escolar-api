package com.escola.api.controller;

import com.escola.api.dto.FrequenciaCompleteResponseDTO;
import com.escola.api.entity.Aluno;
import com.escola.api.entity.Turma;
import com.escola.api.service.AlunoService;
import com.escola.api.service.TurmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
    private final TurmaService turmaService;
    private final AlunoService alunoService;

    public TurmaController(TurmaService turmaService, AlunoService alunoService) {
        this.turmaService = turmaService;
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<Turma> listarTodos() {
        return turmaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarPorId(@PathVariable Integer id) {
        Turma turma = turmaService.buscarPorId(id);
        if (turma != null) {
            return ResponseEntity.ok(turma);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Turma> salvar(@RequestBody Turma turma) {
        Turma salva = turmaService.salvar(turma);
        return ResponseEntity.ok(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizar(@PathVariable Integer id, @RequestBody Turma dados) {
        Turma turma = turmaService.buscarPorId(id);
        if (turma != null) {
            turma.setNome(dados.getNome());
            turma.setPeriodo(dados.getPeriodo());
            turma.setAnoLetivo(dados.getAnoLetivo());
            turma.setEscola(dados.getEscola());
            Turma atualizada = turmaService.salvar(turma);
            return ResponseEntity.ok(atualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Turma turma = turmaService.buscarPorId(id);
        if (turma != null) {
            turmaService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<List<Aluno>> buscarAlunosPorTurma(@PathVariable Integer id) {
        List<Aluno> alunos = alunoService.buscarAlunosPorTurma(id);
        if (alunos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/por-periodo/{periodo}")
    public ResponseEntity<List<Turma>> buscarPorPeriodo(@PathVariable String periodo) {
        List<Turma> turmas = turmaService.buscarPorPeriodo(periodo);
        if (turmas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}/dados-completos")
    public ResponseEntity<FrequenciaCompleteResponseDTO> obterDadosCompletos(
            @PathVariable Integer id,
            @RequestParam Integer mes,
            @RequestParam Integer ano
    ) {
        try {
            System.out.println("\n>>> GET /turmas/" + id + "/dados-completos?mes=" + mes + "&ano=" + ano);
            FrequenciaCompleteResponseDTO dto = turmaService.obterDadosCompletos(id, mes, ano);
            System.out.println("<<< Retornando DTO com sucesso\n");
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
