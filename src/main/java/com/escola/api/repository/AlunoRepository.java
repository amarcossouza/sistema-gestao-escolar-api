package com.escola.api.repository;

import com.escola.api.entity.Aluno;
import com.escola.api.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    List<Aluno> findByTurma(Turma turma);
    
    @Query("SELECT a FROM Aluno a WHERE a.turma.id = :turmaId ORDER BY a.nome ASC")
    List<Aluno> findByTurmaId(@Param("turmaId") Integer turmaId);
}
