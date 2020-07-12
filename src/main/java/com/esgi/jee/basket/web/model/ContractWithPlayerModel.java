package com.esgi.jee.basket.web.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractWithPlayerModel extends RepresentationModel<ContractWithPlayerModel> {

    private LocalDate startDate;

    private LocalDate endDate;

    private PlayerModel player;
}
