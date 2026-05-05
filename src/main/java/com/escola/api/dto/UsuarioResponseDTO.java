package com.escola.api.dto;

import com.escola.api.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO de resposta de usuário (sem senha)")
public class UsuarioResponseDTO {

    private Integer id;
    private String nome;
    private String email;
    private Boolean ativo;
    private Boolean senhaTemporaria;
    private Integer tentativasLogin;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private LocalDateTime ultimoLogin;

    public static UsuarioResponseDTO from(Usuario u) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.id = u.getId();
        dto.nome = u.getNome();
        dto.email = u.getEmail();
        dto.ativo = u.getAtivo();
        dto.senhaTemporaria = u.getSenhaTemporaria();
        dto.tentativasLogin = u.getTentativasLogin();
        dto.criadoEm = u.getCriadoEm();
        dto.atualizadoEm = u.getAtualizadoEm();
        dto.ultimoLogin = u.getUltimoLogin();
        return dto;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Boolean getAtivo() { return ativo; }
    public Boolean getSenhaTemporaria() { return senhaTemporaria; }
    public Integer getTentativasLogin() { return tentativasLogin; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
}
