package com.escola.api.repository;

import com.escola.api.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    List<Turma> findByPeriodo(String periodo);
}
