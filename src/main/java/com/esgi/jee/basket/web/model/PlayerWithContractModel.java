package com.esgi.jee.basket.web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
public class PlayerWithContractModel extends RepresentationModel<PlayerWithContractModel> {

    private String firstname;

    private String lastname;

    private ContractWithTeamModel contract;
}
