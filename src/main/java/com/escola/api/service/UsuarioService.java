package com.escola.api.service;

import com.escola.api.entity.Usuario;
import com.escola.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String login(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return "Usuário não encontrado";
        }
        Usuario usuario = usuarioOpt.get();
        if (usuario.getAtivo() == null || !usuario.getAtivo()) {
            return "Usuário inativo";
        }
        if (!usuario.getSenha().equals(senha)) {
            return "Senha incorreta";
        }
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);
        if (Boolean.TRUE.equals(usuario.getSenhaTemporaria())) {
            return "SENHA_TEMPORARIA - troca obrigatória";
        }
        return "Login realizado com sucesso";
    }

    public String resetarSenha(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return "Usuário não encontrado";
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setSenha("12345");
        usuario.setSenhaTemporaria(true);
        usuarioRepository.save(usuario);
        return "Senha resetada para temporária";
    }

    public String trocarSenha(Integer id, String novaSenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return "Usuário não encontrado";
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(novaSenha);
        usuario.setSenhaTemporaria(false);
        usuarioRepository.save(usuario);
        return "Senha alterada com sucesso";
    }

    public String trocarSenhaComValidacao(String email, String senhaAtual, String senhaNova) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return "Usuário não encontrado";
        }
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getSenha().equals(senhaAtual)) {
            return "Senha atual incorreta";
        }
        usuario.setSenha(senhaNova);
        usuario.setSenhaTemporaria(false);
        usuario.setAtualizadoEm(LocalDateTime.now());
        usuarioRepository.save(usuario);
        return "Senha alterada com sucesso";
    }
}
