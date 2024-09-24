package com.vinicius.locadora.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel<T> {
    public T dados;
    public String mensagem;
    public boolean status = true;
}
