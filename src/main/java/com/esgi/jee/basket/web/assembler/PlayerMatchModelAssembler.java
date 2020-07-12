package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.web.PlayerController;
import com.esgi.jee.basket.web.model.PlayerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class PlayerMatchModelAssembler implements RepresentationModelAssembler<Player, PlayerModel> {

    @Override
    public PlayerModel toModel(Player entity) {

       return PlayerModel.builder()
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .build()
                .add(
                    WebMvcLinkBuilder.linkTo(methodOn(PlayerController.class).getOne(entity.getId())).withSelfRel(),
                    linkTo(methodOn(PlayerController.class).getAll(PageRequest.of(0, 10))).withRel("players")
                );
    }
}
