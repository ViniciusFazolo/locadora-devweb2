package com.vinicius.locadora.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Diretor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String nome;

    @OneToMany(mappedBy = "diretor")
    @JsonIgnore
    List<Titulo> titulos;
}
