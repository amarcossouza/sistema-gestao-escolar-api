package com.escola.api.controller;

import com.escola.api.dto.LoginRequest;
import com.escola.api.dto.TrocarSenhaRequest;
import com.escola.api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();
        String result = usuarioService.login(email, senha);
        if ("Login realizado com sucesso".equals(result)) {
            return ResponseEntity.ok(result);
        } else if ("SENHA_TEMPORARIA - troca obrigatória".equals(result)) {
            return ResponseEntity.status(403).body(result);
        } else {
            return ResponseEntity.status(401).body(result);
        }
    }

    @PostMapping("/usuarios/resetar-senha/{id}")
    public ResponseEntity<String> resetarSenha(@PathVariable Integer id) {
        String result = usuarioService.resetarSenha(id);
        if ("Senha resetada para temporária".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @PostMapping("/usuarios/trocar-senha")
    public ResponseEntity<String> trocarSenha(@RequestBody Map<String, String> body) {
        Integer id = Integer.valueOf(body.get("id"));
        String novaSenha = body.get("novaSenha");
        String result = usuarioService.trocarSenha(id, novaSenha);
        if ("Senha alterada com sucesso".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @PostMapping("/trocar-senha")
    public ResponseEntity<String> trocarSenhaComValidacao(@RequestBody TrocarSenhaRequest request) {
        String result = usuarioService.trocarSenhaComValidacao(
            request.getEmail(),
            request.getSenhaAtual(),
            request.getSenhaNova()
        );
        if ("Senha alterada com sucesso".equals(result)) {
            return ResponseEntity.ok(result);
        } else if ("Senha atual incorreta".equals(result)) {
            return ResponseEntity.status(401).body(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Endpoint de logout - o backend simplesmente confirma o logout
        // A sessão é invalidada no frontend (localStorage, sessionStorage, etc)
        return ResponseEntity.ok("Logout realizado com sucesso");
    }
}
