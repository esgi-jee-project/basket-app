package com.esgi.jee.basket.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class MatchCreateModel extends RepresentationModel<MatchCreateModel> {
    @NotNull
    private LocalDate date;
    @NotNull
    private String place;
    private Long idTeamLocal;
    private Long idTeamOpponent;
    private Integer scoreLocal;
    private Integer scoreOpponent;
}
