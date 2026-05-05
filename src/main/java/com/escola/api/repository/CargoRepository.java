package com.escola.api.repository;

import com.escola.api.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    List<Cargo> findByAtivoTrue();
}
