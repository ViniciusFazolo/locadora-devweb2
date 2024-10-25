package com.vinicius.locadora.DTO.ResponseDTO;

import java.util.Date;
import java.util.List;

import com.vinicius.locadora.model.Titulo;

public record ClasseResponseDTO(
    int id,
    String nome,
    double valor,
    Date prazoDevolucao,
    List<Titulo> titulos
) {
    
}
