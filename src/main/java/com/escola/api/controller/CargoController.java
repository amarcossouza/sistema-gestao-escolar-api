package com.escola.api.controller;

import com.escola.api.entity.Cargo;
import com.escola.api.repository.CargoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoRepository cargoRepository;

    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> listarAtivos() {
        return ResponseEntity.ok(cargoRepository.findByAtivoTrue());
    }
}
