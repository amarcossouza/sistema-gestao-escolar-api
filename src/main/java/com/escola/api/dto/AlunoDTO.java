package com.escola.api.dto;

import com.escola.api.entity.Aluno;

public class AlunoDTO {
    private Integer id;
    private String nome;
    private Integer turmaId;
    private TurmaDTO turma;

    public static class TurmaDTO {
        private Integer id;
        private String nome;

        public TurmaDTO() {}

        public TurmaDTO(Integer id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    public AlunoDTO() {}

    public AlunoDTO(Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        
        if (aluno.getTurma() != null) {
            this.turmaId = aluno.getTurma().getId();
            this.turma = new TurmaDTO(aluno.getTurma().getId(), aluno.getTurma().getNome());
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getTurmaId() { return turmaId; }
    public void setTurmaId(Integer turmaId) { this.turmaId = turmaId; }

    public TurmaDTO getTurma() { return turma; }
    public void setTurma(TurmaDTO turma) { this.turma = turma; }
}
