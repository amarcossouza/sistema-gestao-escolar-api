package com.escola.api.controller;

import com.escola.api.dto.FrequenciaCompleteResponseDTO;
import com.escola.api.entity.Aluno;
import com.escola.api.entity.Turma;
import com.escola.api.service.AlunoService;
import com.escola.api.service.TurmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> obterDadosCompletos(
            @PathVariable Integer id,
            @RequestParam Integer mes,
            @RequestParam Integer ano
    ) {
        try {
            System.out.println("\n>>> GET /turmas/" + id + "/dados-completos?mes=" + mes + "&ano=" + ano);
            
            // Validar parâmetros
            if (id == null || id <= 0) {
                System.err.println("❌ ERRO: ID da turma inválido: " + id);
                return ResponseEntity.badRequest().body("ID da turma inválido");
            }
            if (mes == null || mes < 1 || mes > 12) {
                System.err.println("❌ ERRO: Mês inválido: " + mes);
                return ResponseEntity.badRequest().body("Mês inválido: " + mes);
            }
            if (ano == null || ano < 2020 || ano > 2030) {
                System.err.println("❌ ERRO: Ano inválido: " + ano);
                return ResponseEntity.badRequest().body("Ano inválido: " + ano);
            }
            
            FrequenciaCompleteResponseDTO dto = turmaService.obterDadosCompletos(id, mes, ano);
            System.out.println("<<< Retornando DTO com sucesso\n");
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            System.err.println("❌ ERRO DETALHADO: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            
            // Retornar o erro com mais detalhes
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getClass().getSimpleName());
            errorResponse.put("message", e.getMessage());
            errorResponse.put("turmaId", id);
            errorResponse.put("mes", mes);
            errorResponse.put("ano", ano);
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
