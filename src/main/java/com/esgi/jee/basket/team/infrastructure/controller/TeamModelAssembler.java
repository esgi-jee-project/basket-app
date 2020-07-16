package com.esgi.jee.basket.team.infrastructure.controller;

import com.esgi.jee.basket.team.domain.model.Team;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.web.ContractController;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeamModelAssembler implements RepresentationModelAssembler<Team, TeamModel> {

    @Override
    public TeamModel toModel(Team entity) {
        TeamModel model = new TeamModel(entity.getName(), entity.getCountry(), entity.getPlace());
        model.add(
            linkTo(methodOn(TeamController.class).getById(entity.getId())).withSelfRel(),
            linkTo(TeamController.class).withRel("teams"),
            linkTo(
                                methodOn(ContractController.class)
                                    .getTeamContract(entity.getId(), PageRequest.of(0, 10))
                            )
                            .withRel("contract")
        );

        return model;
    }

    public Team toTeam(TeamModel entity){

        return new Team(entity.getName(), entity.getCountry(), entity.getPlace());
    }
}
