package com.vinicius.locadora.DTO.RequestDTO;

import java.time.LocalDate;

import com.vinicius.locadora.model.Cliente;
import com.vinicius.locadora.model.Item;

public record LocacaoRequestDTO(
    int id,
    LocalDate dtLocacao,
    LocalDate dtDevolucaoPrevista,
    LocalDate dtDevolucaoEfetiva,
    Double valorCobrado,
    Double multaCobrada,
    Cliente cliente,
    Item item
) {
    
}
