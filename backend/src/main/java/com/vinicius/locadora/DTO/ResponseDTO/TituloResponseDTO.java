package com.vinicius.locadora.DTO.ResponseDTO;

import java.util.Date;

import java.util.List;

import com.vinicius.locadora.model.Ator;
import com.vinicius.locadora.model.Classe;
import com.vinicius.locadora.model.Diretor;

public record TituloResponseDTO(
    int id,
    String nome,
    Date ano,
    String sinopse,
    String categoria,
    Classe classe,
    Diretor diretor,
    List<Ator> ator
) {
    
}
