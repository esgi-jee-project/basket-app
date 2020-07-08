package com.esgi.jee.basket.exception;

public class MatchSearchNotFoundException extends RuntimeException {

    public MatchSearchNotFoundException(String id){

        super("Could not find search " + id);
    }
}
