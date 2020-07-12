package com.esgi.jee.basket.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class PlayerModel extends RepresentationModel<PlayerModel> {

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;
}
