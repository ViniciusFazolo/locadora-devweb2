package com.vinicius.locadora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.locadora.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByNumOrder();
}
