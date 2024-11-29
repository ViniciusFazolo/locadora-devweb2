package com.vinicius.locadora.exceptions;

public class CancelarLocacaoException extends RuntimeException{
    public CancelarLocacaoException(){
        super("Não é possível cancelar esta locação, pois, já foi realizado pagamento.");
    }
}
