package com.vinicius.locadora.DTO.RequestDTO;

import java.util.Date;

import com.vinicius.locadora.model.Titulo;

public record ItemRequestDTO(
    int id,
    int numSerie,
    Date dtAquisicao,
    String tipoItem,
    Titulo titulo
) {
    
}
