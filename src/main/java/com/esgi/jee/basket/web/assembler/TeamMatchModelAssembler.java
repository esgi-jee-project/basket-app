package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.web.TeamController;
import com.esgi.jee.basket.web.model.TeamModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeamMatchModelAssembler implements RepresentationModelAssembler<Team, TeamModel> {

    @Override
    public TeamModel toModel(Team entity) {
        TeamModel model = new TeamModel(entity.getName(), entity.getCountry(), entity.getCountry());

        model.add(
            WebMvcLinkBuilder.linkTo(methodOn(TeamController.class).getById(entity.getId())).withSelfRel()
//          linkTo(methodOn(TeamController.class).getAll(PageRequest.of(0, 10), authentication)).withRel("teams")
        );

        return model;
    }
}
