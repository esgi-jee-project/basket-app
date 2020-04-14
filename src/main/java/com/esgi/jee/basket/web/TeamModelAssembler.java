package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Team;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeamModelAssembler implements RepresentationModelAssembler<Team, TeamModel> {
    @Override
    public TeamModel toModel(Team entity) {
        TeamModel model = new TeamModel();
        model.setName(entity.getName());
        model.setCountry(entity.getCountry());
        model.add(
            linkTo(methodOn(TeamController.class).getById(entity.getId())).withSelfRel(),
            linkTo(methodOn(TeamController.class).getAll(PageRequest.of(0, 10))).withRel("teams")
        );

        return model;
    }
}
