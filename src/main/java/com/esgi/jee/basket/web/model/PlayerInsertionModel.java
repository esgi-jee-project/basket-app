package com.esgi.jee.basket.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class PlayerInsertionModel extends RepresentationModel<PlayerInsertionModel> {

    @NotNull
    private Long id;

}
