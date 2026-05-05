package com.escola.api.repository;

import com.escola.api.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Optional<Professor> findByEmail(String email);
    List<Professor> findByAtivoTrue();
    List<Professor> findBySiglaMateria(String siglaMateria);
}
