package com.esgi.jee.basket.web.model;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.Team;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatchModel extends RepresentationModel<TeamModel> {

    @NotNull
    private LocalDate date;
    @NotNull
    private String place;
    private TeamModel nameLocal;
    private TeamModel nameOpponent;
    private Integer scoreLocal;
    private Integer scoreOpponent;
    private List<PlayerModel> playerTeamLocal = new ArrayList<>();
    private List<PlayerModel> playerTeamOpponent = new ArrayList<>();


}
