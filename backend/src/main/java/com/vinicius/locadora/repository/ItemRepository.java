package com.vinicius.locadora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.vinicius.locadora.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByNumSerie(@Param("numSerie") int numSerie);
}
