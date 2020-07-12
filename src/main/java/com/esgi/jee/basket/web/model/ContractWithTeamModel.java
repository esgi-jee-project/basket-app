package com.esgi.jee.basket.web.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractWithTeamModel extends RepresentationModel<ContractWithTeamModel> {

    private LocalDate startDate;

    private LocalDate endDate;

    private TeamModel team;
}
