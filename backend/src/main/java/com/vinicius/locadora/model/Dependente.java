package com.vinicius.locadora.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dependente extends Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @JsonIgnoreProperties("dependentes")
    @ManyToOne
    private Socio socio;
}
