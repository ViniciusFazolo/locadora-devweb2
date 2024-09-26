package com.vinicius.locadora.DTO.RequestDTO;

import java.util.Date;

public record ClasseRequestDTO(
    String nome,
    double valor,
    Date prazoDevolucao
) {
    
}
