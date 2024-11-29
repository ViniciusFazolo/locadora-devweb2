package com.vinicius.locadora.model;

import java.util.List;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Socio extends Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String cpf;
    private String endereco;
    private String tel;

    @OneToMany(mappedBy = "socio")
    private List<Dependente> dependentes;
}
