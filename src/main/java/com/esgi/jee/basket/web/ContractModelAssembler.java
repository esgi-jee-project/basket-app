package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Contract;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class ContractModelAssembler implements RepresentationModelAssembler<Contract, ContractModel> {

    private final TeamModelAssembler teamModelAssembler;

    @Override
    public ContractModel toModel(Contract entity) {
        ContractModel model = new ContractModel();
        model.setStartDate(entity.getStartDate());
        model.setEndDate(entity.getEndDate());

        //model.setTeam(teamModelAssembler.toModel(entity.getTeam()));

        model.add(
                linkTo(methodOn(ContractController.class).selectOne(entity.getId())).withSelfRel()
        );

        return model;
    }
}
