package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Match;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class MatchModelAssembler implements RepresentationModelAssembler<Match, MatchModel> {

    public MatchModel toModel(Match entity) {
        MatchModel model = new MatchModel();
        model.setDate(entity.getDate());
        model.setPlace(entity.getPlace());
        model.setNameLocal(entity.getNameLocal());
        model.setNameOpponent(entity.getNameOpponent());
        model.setScoreLocal(entity.getScoreLocal());
        model.setScoreOpponent(entity.getScoreOpponent());

        /*model.add(
                linkTo(methodOn(MatchController.class).getById(entity.getId())).withSelfRel()
        );*/

        return model;
    }
}
