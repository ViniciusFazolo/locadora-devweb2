package com.vinicius.locadora.DTO.ResponseDTO;

import java.util.List;

import com.vinicius.locadora.model.Titulo;

public record DiretorResponseDTO(
    int id,
    String nome,
    List<Titulo> titulos
) {
}
