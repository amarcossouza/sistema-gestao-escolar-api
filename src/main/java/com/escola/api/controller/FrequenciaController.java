package com.escola.api.controller;

import com.escola.api.dto.FrequenciaRequest;
import com.escola.api.entity.Frequencia;
import com.escola.api.service.FrequenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/frequencias")
public class FrequenciaController {
    private final FrequenciaService frequenciaService;

    public FrequenciaController(FrequenciaService frequenciaService) {
        this.frequenciaService = frequenciaService;
    }

    @GetMapping
    public List<Frequencia> listar(
            @RequestParam(required = false) Integer turmaId,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim
    ) {
        if (turmaId != null && dataInicio != null && dataFim != null) {
            return frequenciaService.buscarPorTurmaEPeriodo(turmaId, LocalDate.parse(dataInicio), LocalDate.parse(dataFim));
        }
        return frequenciaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frequencia> buscarPorId(@PathVariable Integer id) {
        Frequencia frequencia = frequenciaService.buscarPorId(id);
        if (frequencia != null) {
            return ResponseEntity.ok(frequencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody FrequenciaRequest request) {
        try {
            Frequencia salva = frequenciaService.salvar(request);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar frequência: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frequencia> atualizar(@PathVariable Integer id, @RequestBody Frequencia dados) {
        Frequencia frequencia = frequenciaService.buscarPorId(id);
        if (frequencia != null) {
            frequencia.setData(dados.getData());
            frequencia.setStatus(dados.getStatus());
            frequencia.setAluno(dados.getAluno());
            frequencia.setTurma(dados.getTurma());
            Frequencia atualizada = frequenciaService.salvar(frequencia);
            return ResponseEntity.ok(atualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Frequencia frequencia = frequenciaService.buscarPorId(id);
        if (frequencia != null) {
            frequenciaService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/salvar-lote")
    public ResponseEntity<String> salvarLote(@RequestBody List<FrequenciaRequest> frequencias) {
        try {
            frequenciaService.salvarLote(frequencias);
            return ResponseEntity.ok("Frequências salvas com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar frequências: " + e.getMessage());
        }
    }
}
