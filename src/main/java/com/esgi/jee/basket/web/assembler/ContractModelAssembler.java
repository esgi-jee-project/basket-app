package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.team.infrastructure.controller.TeamModelAssembler;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeamAdapter;
import com.esgi.jee.basket.web.ContractController;
import com.esgi.jee.basket.web.PlayerController;
import com.esgi.jee.basket.team.infrastructure.controller.TeamController;
import com.esgi.jee.basket.web.model.ContractModel;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class ContractModelAssembler implements RepresentationModelAssembler<Contract, ContractModel> {

    private final TeamModelAssembler teamModelAssembler;
    private final PlayerModelAssembler playerModelAssembler;

    @Override
    public ContractModel toModel(Contract entity) {
        ContractModel.ContractModelBuilder builder = ContractModel.builder();
        builder.startDate(entity.getStartDate());
        builder.endDate(entity.getEndDate());
        builder.team(teamModelAssembler.toModel(HibernateTeamAdapter.modelToTeam(entity.getTeam())));
        builder.player(playerModelAssembler.toModel(entity.getPlayer()));

        return builder.build()
                .add(
                    linkTo(methodOn(ContractController.class).selectOne(entity.getId())).withSelfRel(),
                    linkTo(methodOn(TeamController.class).getById(entity.getTeam().getId())).withRel("team"),
                    linkTo(methodOn(PlayerController.class).getOne(entity.getPlayer().getId())).withRel("player")
                );
    }
}
