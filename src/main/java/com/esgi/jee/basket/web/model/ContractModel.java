package com.esgi.jee.basket.web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ContractModel extends RepresentationModel<ContractModel> {

    private LocalDate startDate;

    private LocalDate endDate;

    private TeamModel team;

    private PlayerModel player;
}
