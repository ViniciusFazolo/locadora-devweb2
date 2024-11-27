package com.vinicius.locadora.model;

import java.time.LocalDate;

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
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate dtLocacao;
    private LocalDate dtDevolucaoPrevista;
    private LocalDate dtDevolucaoEfetiva;
    private Double valorCobrado;
    private Double multaCobrada;

    @ManyToOne
    private Cliente cliente;
    
    @ManyToOne
    private Item item;
}
