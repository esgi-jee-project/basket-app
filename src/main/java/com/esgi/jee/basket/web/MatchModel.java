package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.Team;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MatchModel extends RepresentationModel<TeamModel> {

    private LocalDate date;
    private String place;
    private Team nameLocal;
    private Team nameOpponent;
    private Integer scoreLocal;
    private Integer scoreOpponent;
    private List<PlayerModel> playerTeamLocal;
    private List<PlayerModel> playerTeamOpponent;


}
