package com.vinicius.locadora.exceptions;

public class LocacoesAtrasadasException extends RuntimeException{
    public LocacoesAtrasadasException(String locacoes){
        super("Erro! Cliente possui locações pendentes. Locações: " + locacoes);
    }
}
