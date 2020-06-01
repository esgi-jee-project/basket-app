package com.esgi.jee.basket.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
public class MatchModel extends RepresentationModel<TeamModel> {

    private LocalDate date;
    private String place;
    private String nameLocal;
    private String nameOpponent;
    private Integer scoreLocal;
    private Integer scoreOpponent;
}
