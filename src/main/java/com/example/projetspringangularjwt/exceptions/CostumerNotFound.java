package com.example.projetspringangularjwt.exceptions;

public class CostumerNotFound extends RuntimeException{
    public CostumerNotFound(String message) {
        super(message);
    }
}
