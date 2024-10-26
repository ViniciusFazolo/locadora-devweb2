package com.vinicius.locadora.DTO.RequestDTO;

import java.util.Date;
import java.util.List;

import com.vinicius.locadora.model.Titulo;

public record ClasseRequestDTO(
    String nome,
    Double valor,
    Date prazoDevolucao,
    List<Titulo> titulos
) {
    
}
