package com.vinicius.locadora.DTO.ResponseDTO;

import java.util.Date;

public record ClasseResponseDTO(
    int id,
    String nome,
    double valor,
    Date prazoDevolucao
) {
    
}
