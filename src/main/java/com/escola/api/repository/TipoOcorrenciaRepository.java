package com.escola.api.repository;

import com.escola.api.entity.TipoOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TipoOcorrenciaRepository extends JpaRepository<TipoOcorrencia, Integer> {
    List<TipoOcorrencia> findByAtivoTrue();
    
    @Query("SELECT t FROM TipoOcorrencia t WHERE t.ativo = true ORDER BY t.nome ASC")
    List<TipoOcorrencia> findAllAtivos();
    
    TipoOcorrencia findByNome(String nome);
}
