package com.esgi.jee.basket.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ContractModel extends RepresentationModel<ContractModel> {

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private TeamModel team;

    private PlayerModel player;
}
