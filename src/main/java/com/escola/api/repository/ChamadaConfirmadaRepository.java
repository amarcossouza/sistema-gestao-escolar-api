package com.escola.api.repository;

import com.escola.api.entity.ChamadaConfirmada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ChamadaConfirmadaRepository extends JpaRepository<ChamadaConfirmada, Integer> {
    ChamadaConfirmada findByTurmaIdAndDataChamada(Integer turmaId, LocalDate dataChamada);
    
    @Query("SELECT cc FROM ChamadaConfirmada cc " +
           "WHERE cc.turmaId = :turmaId " +
           "AND cc.dataChamada >= :dataInicio " +
           "AND cc.dataChamada <= :dataFim " +
           "ORDER BY cc.dataChamada ASC")
    List<ChamadaConfirmada> findByTurmaIdAndDataChamadaBetween(
        @Param("turmaId") Integer turmaId,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );
}
