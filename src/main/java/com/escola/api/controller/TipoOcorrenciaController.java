package com.escola.api.controller;

import com.escola.api.entity.TipoOcorrencia;
import com.escola.api.service.TipoOcorrenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-ocorrencias")
public class TipoOcorrenciaController {
    private final TipoOcorrenciaService service;

    public TipoOcorrenciaController(TipoOcorrenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoOcorrencia>> listarTodos() {
        List<TipoOcorrencia> tipos = service.listarTodos();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<TipoOcorrencia>> listarAtivos() {
        List<TipoOcorrencia> tipos = service.listarAtivos();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoOcorrencia> obterPorId(@PathVariable Integer id) {
        Optional<TipoOcorrencia> tipo = service.obterPorId(id);
        return tipo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoOcorrencia> criar(@RequestBody TipoOcorrencia tipoOcorrencia) {
        try {
            TipoOcorrencia criado = service.criar(tipoOcorrencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoOcorrencia> atualizar(
            @PathVariable Integer id,
            @RequestBody TipoOcorrencia tipoOcorrencia) {
        try {
            TipoOcorrencia atualizado = service.atualizar(id, tipoOcorrencia);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<TipoOcorrencia> desativar(@PathVariable Integer id) {
        try {
            service.desativar(id);
            Optional<TipoOcorrencia> tipo = service.obterPorId(id);
            return tipo.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
