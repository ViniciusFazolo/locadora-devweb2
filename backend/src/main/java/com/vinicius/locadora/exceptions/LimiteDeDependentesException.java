package com.vinicius.locadora.exceptions;

public class LimiteDeDependentesException extends RuntimeException{
    public LimiteDeDependentesException(){
        super("Este sócio pode ter no máximo 3 dependentes ativos");
    }
}
