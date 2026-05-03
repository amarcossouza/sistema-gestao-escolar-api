package com.escola.api.controller;

import com.escola.api.dto.OcorrenciaCreateDTO;
import com.escola.api.dto.OcorrenciaResponseDTO;
import com.escola.api.entity.Ocorrencia;
import com.escola.api.service.OcorrenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {
    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @GetMapping
    public ResponseEntity<List<OcorrenciaResponseDTO>> buscarTodas() {
        List<Ocorrencia> ocorrencias = ocorrenciaService.buscarTodas();
        if (ocorrencias.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<OcorrenciaResponseDTO> dtos = ocorrencias.stream()
                .map(OcorrenciaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<OcorrenciaResponseDTO> salvar(@RequestBody OcorrenciaCreateDTO dto) {
        try {
            Ocorrencia ocorrencia = ocorrenciaService.criarOcorrencia(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OcorrenciaResponseDTO(ocorrencia));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<OcorrenciaResponseDTO>> buscarPorAluno(@PathVariable Integer alunoId) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.buscarPorAluno(alunoId);
        if (ocorrencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<OcorrenciaResponseDTO> dtos = ocorrencias.stream()
                .map(OcorrenciaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/aluno/{alunoId}/turma/{turmaId}")
    public ResponseEntity<List<OcorrenciaResponseDTO>> buscarPorAlunoETurma(
            @PathVariable Integer alunoId,
            @PathVariable Integer turmaId
    ) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.buscarPorAlunoETurma(alunoId, turmaId);
        if (ocorrencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<OcorrenciaResponseDTO> dtos = ocorrencias.stream()
                .map(OcorrenciaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaResponseDTO> buscarPorId(@PathVariable Integer id) {
        Ocorrencia ocorrencia = ocorrenciaService.buscarPorId(id);
        if (ocorrencia != null) {
            return ResponseEntity.ok(new OcorrenciaResponseDTO(ocorrencia));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Ocorrencia ocorrencia = ocorrenciaService.buscarPorId(id);
        if (ocorrencia != null) {
            ocorrenciaService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
