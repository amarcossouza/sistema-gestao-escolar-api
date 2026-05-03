package com.escola.api.repository;

import com.escola.api.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscolaRepository extends JpaRepository<Escola, Integer> {
}
