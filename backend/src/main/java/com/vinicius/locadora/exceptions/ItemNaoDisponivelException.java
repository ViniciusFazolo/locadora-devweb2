package com.vinicius.locadora.exceptions;

import java.time.LocalDate;

public class ItemNaoDisponivelException extends RuntimeException{
    public ItemNaoDisponivelException(LocalDate dataPrevista){
        super("Item não disponível. Data prevista de entrega: " + dataPrevista);
    }
}
