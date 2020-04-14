package com.esgi.jee.basket.web;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class TeamModel extends RepresentationModel<TeamModel> {

    private String name;

    private String country;
}
