package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.db.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class PlayerMatchModelAssembler implements RepresentationModelAssembler<Player, PlayerModel> {

    private final ContractModelAssembler contractModelAssembler;

    @Override
    public PlayerModel toModel(Player entity) {

        PlayerModel model = new PlayerModel();
        model.setFirstname(entity.getFirstname());
        model.setLastname(entity.getLastname());
         
        model.add(
                linkTo(methodOn(PlayerController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(PlayerController.class).getAll(PageRequest.of(0, 10))).withRel("players")
        );


        return model;
    }
}
