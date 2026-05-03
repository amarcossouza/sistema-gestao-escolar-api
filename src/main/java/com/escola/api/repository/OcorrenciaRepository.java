package com.escola.api.repository;

import com.escola.api.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {
    @Query("SELECT DISTINCT o FROM Ocorrencia o LEFT JOIN FETCH o.tipoOcorrencia WHERE o.alunoId = :alunoId ORDER BY o.dataOcorrencia DESC, o.horaOcorrencia DESC")
    List<Ocorrencia> findByAlunoIdOrderByDataOcorrenciaDescHoraOcorrenciaDesc(@Param("alunoId") Integer alunoId);
    
    @Query("SELECT DISTINCT o FROM Ocorrencia o LEFT JOIN FETCH o.tipoOcorrencia WHERE o.alunoId = :alunoId AND o.turmaId = :turmaId ORDER BY o.dataOcorrencia DESC, o.horaOcorrencia DESC")
    List<Ocorrencia> findByAlunoIdAndTurmaIdOrderByDataOcorrenciaDescHoraOcorrenciaDesc(@Param("alunoId") Integer alunoId, @Param("turmaId") Integer turmaId);
    long countByAlunoIdAndTurmaId(Integer alunoId, Integer turmaId);
    Integer countByAlunoId(Integer alunoId);
    
    // Query otimizada: traz contagem de ocorrências de uma turma em uma única query
    @Query("SELECT new map(o.alunoId as alunoId, COUNT(o) as count) " +
           "FROM Ocorrencia o " +
           "WHERE o.turmaId = :turmaId " +
           "GROUP BY o.alunoId " +
           "ORDER BY o.alunoId ASC")
    List<Map<String, Object>> countOcorrenciasByTurmaId(@Param("turmaId") Integer turmaId);
}
