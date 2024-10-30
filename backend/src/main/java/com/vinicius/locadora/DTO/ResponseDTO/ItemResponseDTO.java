package com.vinicius.locadora.DTO.ResponseDTO;

import java.util.Date;

import com.vinicius.locadora.model.Titulo;

public record ItemResponseDTO(
    int id,
    int numSerie,
    Date dtAquisicao,
    String tipoItem,
    Titulo titulo
) {
    
}
