package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.web.ContractController;
import com.esgi.jee.basket.web.TeamController;
import com.esgi.jee.basket.web.model.TeamModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeamModelAssembler implements RepresentationModelAssembler<Team, TeamModel> {

    @Override
    public TeamModel toModel(Team entity) {
        TeamModel model = new TeamModel(entity.getName(), entity.getCountry());
        model.add(
            WebMvcLinkBuilder.linkTo(methodOn(TeamController.class).getById(entity.getId())).withSelfRel(),
            linkTo(TeamController.class).withRel("teams"),
            WebMvcLinkBuilder.linkTo(
                                methodOn(ContractController.class)
                                    .getPlayerInTeam(entity.getId(), PageRequest.of(0, 10))
                            )
                            .withRel("contract")
        );

        return model;
    }
}
