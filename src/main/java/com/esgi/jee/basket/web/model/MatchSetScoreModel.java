package com.esgi.jee.basket.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatchSetScoreModel extends RepresentationModel<TeamModel> {

    @NotNull
    private Integer scoreLocalTeam;
    @NotNull
    private Integer scoreOpponentTeam;
}
