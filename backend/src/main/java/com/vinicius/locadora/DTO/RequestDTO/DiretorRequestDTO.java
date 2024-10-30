package com.vinicius.locadora.DTO.RequestDTO;

import java.util.List;

import com.vinicius.locadora.model.Titulo;

public record DiretorRequestDTO(
    int id,
    String nome,
    List<Titulo> titulos
) {
}
