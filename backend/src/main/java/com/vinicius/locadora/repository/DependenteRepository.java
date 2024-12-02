package com.vinicius.locadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.locadora.model.Dependente;
import com.vinicius.locadora.model.Socio;

public interface DependenteRepository extends JpaRepository<Dependente, Integer> {
    List<Dependente> findBySocio(Socio socio);
    boolean existsByNumInscricao(int numInscricao);
}
