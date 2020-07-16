package com.esgi.jee.basket.team.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class TeamModel extends RepresentationModel<TeamModel> {

    @NotNull
    private String name;

    @NotNull
    private String country;

    @NotNull
    private String place;
}
