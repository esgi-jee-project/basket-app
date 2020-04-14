package com.esgi.jee.basket.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class PlayerModel extends RepresentationModel<PlayerModel> {

    private String firstname;

    private String lastname;

    private ContractModel currentContract;
}
