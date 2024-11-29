package com.vinicius.locadora.DTO.RequestDTO;

import java.util.List;

import com.vinicius.locadora.model.Titulo;

public record ClasseRequestDTO(
    int id,
    String nome,
    Double valor,
    Integer prazoDevolucao,
    List<Titulo> titulos
) {
    
}
