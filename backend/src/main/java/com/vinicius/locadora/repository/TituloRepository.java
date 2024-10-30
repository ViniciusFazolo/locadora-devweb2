package com.vinicius.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.locadora.model.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Integer> {
    
}
