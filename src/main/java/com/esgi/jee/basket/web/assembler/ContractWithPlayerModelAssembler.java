package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.web.ContractController;
import com.esgi.jee.basket.web.model.ContractWithPlayerModel;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class ContractWithPlayerModelAssembler implements RepresentationModelAssembler<Contract, ContractWithPlayerModel> {

    private PlayerModelAssembler playerModelAssembler;

    @Override
    public ContractWithPlayerModel toModel(Contract entity) {

        ContractWithPlayerModel.ContractWithPlayerModelBuilder builder = ContractWithPlayerModel.builder();
        builder.startDate(entity.getStartDate());
        builder.endDate(entity.getEndDate());
        builder.player(playerModelAssembler.toModel(entity.getPlayer()));

        return builder.build()
                .add(linkTo(methodOn(ContractController.class).selectOne(entity.getId())).withSelfRel());
    }
}
