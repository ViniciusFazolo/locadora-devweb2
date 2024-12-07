package com.vinicius.locadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.locadora.model.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Integer> {
    
    @Query("SELECT t FROM Titulo t WHERE t.nome LIKE :ator%")
    List<Titulo> findByNome(@Param("ator") String ator);

    @Query("SELECT t FROM Titulo t WHERE t.categoria LIKE :categoria%")
    List<Titulo> findByCategoria(@Param("categoria") String categoria);

    @Query("SELECT t FROM Titulo t JOIN Ator a ON a.id = t.id WHERE a.nome LIKE :ator%")
    List<Titulo> findByAtor(@Param("ator") String ator);
}
