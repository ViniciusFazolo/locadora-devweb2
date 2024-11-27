package com.vinicius.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.locadora.model.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {
    
}
