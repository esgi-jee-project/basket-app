package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.web.MatchController;
import com.esgi.jee.basket.web.model.MatchModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
@AllArgsConstructor
public class MatchModelAssembler implements RepresentationModelAssembler<Match, MatchModel> {

    private TeamModelAssembler teamMatchModelAssembler;
    private PlayerModelAssembler playerModelAssembler;

    public MatchModel toModel(Match entity) {
        MatchModel model = new MatchModel();
        model.setDate(LocalDate.parse(entity.getDate()));
        model.setPlace(entity.getPlace());
        model.setNameLocal(teamMatchModelAssembler.toModel(entity.getIdNameLocal()));
        model.setNameOpponent(teamMatchModelAssembler.toModel(entity.getIdNameOpponent()));
        model.setScoreLocal(entity.getScoreLocal());
        model.setScoreOpponent(entity.getScoreOpponent());
        model.add(
                WebMvcLinkBuilder.linkTo(methodOn(MatchController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(MatchController.class).getAll(PageRequest.of(0, 10))).withRel("players")
        );
//        model.setPlayerTeamLocal(entity.getPlayerTeamLocal().stream().map(playerModelAssembler::toModel).collect(Collectors.toList()));
        //model.setPlayerTeamOpponent(entity.getPlayerTeamOpponent());

        return model;
    }
}
