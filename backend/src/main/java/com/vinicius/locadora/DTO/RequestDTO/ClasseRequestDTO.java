package com.vinicius.locadora.DTO.RequestDTO;

import java.util.Date;

public record ClasseRequestDTO(
    String nome,
    Double valor,
    Date prazoDevolucao
) {
    
}
