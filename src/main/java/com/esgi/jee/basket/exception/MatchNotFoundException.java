package com.esgi.jee.basket.exception;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(String id){

        super("Could not find player " + id);
    }
}
