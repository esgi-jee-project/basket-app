package com.esgi.jee.basket.exception;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(Long id){

        super("Could not find player " + id);
    }
}
