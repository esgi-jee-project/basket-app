package com.esgi.jee.basket;

import com.esgi.jee.basket.db.ContractRepository;
import com.esgi.jee.basket.exception.ContractNotFoundException;
import com.esgi.jee.basket.exception.TeamNotFoundException;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String teamNotFound(TeamNotFoundException exception){

        return exception.getMessage();
    }
     
      
    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String playerNotFound(PlayerNotFoundException exception){

        return exception.getMessage();
    }

    @ExceptionHandler(ContractNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String contractNotFound(ContractNotFoundException exception){

        return exception.getMessage();
    }
}
