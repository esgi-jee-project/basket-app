package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.web.ContractController;
import com.esgi.jee.basket.web.model.ContractWithTeamModel;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class ContractWithTeamModelAssembler implements RepresentationModelAssembler<Contract, ContractWithTeamModel> {

    private TeamModelAssembler teamModelAssembler;

    @Override
    public ContractWithTeamModel toModel(Contract entity) {

        ContractWithTeamModel.ContractWithTeamModelBuilder builder = ContractWithTeamModel.builder();
        builder.startDate(entity.getStartDate());
        builder.endDate(entity.getEndDate());
        builder.team(teamModelAssembler.toModel(entity.getTeam()));

        return builder.build()
                .add(linkTo(methodOn(ContractController.class).selectOne(entity.getId())).withSelfRel());
    }
}
