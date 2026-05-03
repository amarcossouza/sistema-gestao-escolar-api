package com.escola.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "esc_chamada_confirmada", uniqueConstraints = @UniqueConstraint(columnNames = {"turma_id", "data_chamada"}))
public class ChamadaConfirmada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "turma_id", nullable = false)
    private Integer turmaId;

    @Column(name = "data_chamada", nullable = false)
    private LocalDate dataChamada;

    @Column(name = "data_hora_confirmacao", nullable = false)
    private LocalDateTime dataHoraConfirmacao;

    @Column(name = "email_professor", nullable = false, length = 255)
    private String emailProfessor;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getTurmaId() { return turmaId; }
    public void setTurmaId(Integer turmaId) { this.turmaId = turmaId; }

    public LocalDate getDataChamada() { return dataChamada; }
    public void setDataChamada(LocalDate dataChamada) { this.dataChamada = dataChamada; }

    public LocalDateTime getDataHoraConfirmacao() { return dataHoraConfirmacao; }
    public void setDataHoraConfirmacao(LocalDateTime dataHoraConfirmacao) { this.dataHoraConfirmacao = dataHoraConfirmacao; }

    public String getEmailProfessor() { return emailProfessor; }
    public void setEmailProfessor(String emailProfessor) { this.emailProfessor = emailProfessor; }
}
