package com.esgi.jee.basket.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(Long id){

        super("Could not find team " + id);
    }
}
