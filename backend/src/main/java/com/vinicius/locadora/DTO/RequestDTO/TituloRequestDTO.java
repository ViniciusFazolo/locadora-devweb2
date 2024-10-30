package com.vinicius.locadora.DTO.RequestDTO;

import java.util.Date;
import java.util.List;

import com.vinicius.locadora.model.Ator;
import com.vinicius.locadora.model.Classe;
import com.vinicius.locadora.model.Diretor;
import com.vinicius.locadora.model.Item;

public record TituloRequestDTO(
    int id,
    String nome,
    Date ano,
    String sinopse,
    String categoria,
    Classe classe,
    Diretor diretor,
    List<Ator> ator,
    List<Item> items
) {
    
}
