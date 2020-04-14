package com.esgi.jee.basket.exception;

public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(Long id){

        super("Could not find contract " + id);
    }
}
