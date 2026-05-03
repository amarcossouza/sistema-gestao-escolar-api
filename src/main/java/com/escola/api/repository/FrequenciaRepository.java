package com.escola.api.repository;

import com.escola.api.entity.Aluno;
import com.escola.api.entity.Frequencia;
import com.escola.api.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Integer> {
    Frequencia findByAlunoAndTurmaAndData(Aluno aluno, Turma turma, LocalDate data);
    List<Frequencia> findByTurmaAndDataBetween(Turma turma, LocalDate dataInicio, LocalDate dataFim);
    
    @Query("SELECT f FROM Frequencia f " +
           "WHERE f.turma.id = :turmaId " +
           "AND f.data >= :dataInicio " +
           "AND f.data <= :dataFim " +
           "ORDER BY f.data ASC")
    List<Frequencia> findByTurmaIdAndDataBetween(
        @Param("turmaId") Integer turmaId,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );
}
