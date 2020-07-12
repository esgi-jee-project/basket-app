package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.web.PlayerController;
import com.esgi.jee.basket.web.model.PlayerWithContractModel;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class PlayerWithContractModelAssembler implements RepresentationModelAssembler<Player, PlayerWithContractModel> {

    private final ContractWithTeamModelAssembler contractWithTeamModelAssembler;

    @Override
    public PlayerWithContractModel toModel(Player entity) {

        PlayerWithContractModel.PlayerWithContractModelBuilder builder = PlayerWithContractModel.builder();
        builder.firstname(entity.getFirstname());
        builder.lastname(entity.getLastname());

        if(entity.getCurrentContract() != null)

            builder.contract(contractWithTeamModelAssembler.toModel(entity.getCurrentContract()));

        return builder.build()
                .add(
                        linkTo(methodOn(PlayerController.class).getOne(entity.getId())).withSelfRel(),
                        linkTo(methodOn(PlayerController.class)).withRel("player")
                );
    }
}
