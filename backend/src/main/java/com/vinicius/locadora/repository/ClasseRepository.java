package com.vinicius.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.locadora.model.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Integer>{
    
}
