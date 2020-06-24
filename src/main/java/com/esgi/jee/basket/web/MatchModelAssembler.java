package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.Team;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class MatchModelAssembler implements RepresentationModelAssembler<Match, MatchModel> {
    public MatchModelAssembler(TeamMatchModelAssembler teamMatchModelAssembler) {
        this.teamMatchModelAssembler = teamMatchModelAssembler;
    }

    private TeamMatchModelAssembler teamMatchModelAssembler;

    public MatchModel toModel(Match entity) {
        MatchModel model = new MatchModel();
        model.setDate(entity.getDate());
        model.setPlace(entity.getPlace());
        model.setNameLocal(teamMatchModelAssembler.toModel(entity.getIdNameLocal()));
        model.setNameOpponent(teamMatchModelAssembler.toModel(entity.getIdNameOpponent()));
        model.setScoreLocal(entity.getScoreLocal());
        model.setScoreOpponent(entity.getScoreOpponent());
        model.add(
                linkTo(methodOn(MatchController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(MatchController.class).getAll(PageRequest.of(0, 10))).withRel("players")
        );
        //model.setPlayerTeamLocal(entity.getPlayerTeamLocal());
        //model.setPlayerTeamOpponent(entity.getPlayerTeamOpponent());


        /*model.add(
                linkTo(methodOn(MatchController.class).getById(entity.getId())).withSelfRel()
        );*/

        return model;
    }
}
