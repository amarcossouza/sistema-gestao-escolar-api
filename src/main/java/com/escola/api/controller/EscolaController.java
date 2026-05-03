package com.escola.api.controller;

import com.escola.api.entity.Escola;
import com.escola.api.repository.EscolaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/escolas")
public class EscolaController {
    private final EscolaRepository escolaRepository;

    public EscolaController(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }

    @GetMapping
    public List<Escola> listarTodos() {
        return escolaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Escola> buscarPorId(@PathVariable int id) {
        Optional<Escola> escola = escolaRepository.findById(id);
        return escola.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Escola> salvar(@RequestBody Escola escola) {
        Escola salva = escolaRepository.save(escola);
        return ResponseEntity.ok(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Escola> atualizar(@PathVariable int id, @RequestBody Escola dados) {
        Optional<Escola> escolaOpt = escolaRepository.findById(id);
        if (escolaOpt.isPresent()) {
            Escola escola = escolaOpt.get();
            escola.setNome(dados.getNome());
            return ResponseEntity.ok(escolaRepository.save(escola));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        Optional<Escola> escolaOpt = escolaRepository.findById(id);
        if (escolaOpt.isPresent()) {
            escolaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
