package com.vinicius.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.locadora.model.Socio;

public interface SocioRepository extends JpaRepository<Socio, Integer> {
    boolean existsByNumInscricao(int numInscricao);
}
