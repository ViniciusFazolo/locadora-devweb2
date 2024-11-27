package com.vinicius.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.locadora.model.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente, Integer> {
    
}
