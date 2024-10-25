package com.vinicius.locadora.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Titulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private Date ano;
    private String sinopse;
    private String categoria;

    @OneToMany(mappedBy = "titulo")
    private List<Item> items;

    @ManyToOne
    private Diretor diretor;

    @ManyToMany(mappedBy = "titulo")
    private List<Ator> ator;

    @ManyToOne
    private Classe classe;
}
