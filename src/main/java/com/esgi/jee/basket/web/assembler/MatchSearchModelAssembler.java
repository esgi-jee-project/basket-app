package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.MatchSearch;
import com.esgi.jee.basket.web.MatchController;
import com.esgi.jee.basket.web.model.MatchSearchModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class MatchSearchModelAssembler  implements RepresentationModelAssembler<MatchSearch, MatchSearchModel> {

    private final MatchModelAssembler matchModelAssembler;

    @Override
    public MatchSearchModel toModel(MatchSearch entity) {
        MatchSearchModel matchSearchModel = MatchSearchModel.builder()
                                                                .ipAddress(entity.getIpAddress())
                                                                .research(entity.getResearch())
                                                                .totalResult(entity.getTotalResult())
                                                                .result(entity.getResult().stream().map(matchModelAssembler::toModel).collect(Collectors.toList()))
                                                        .build();

        matchSearchModel.add(
                linkTo(methodOn(MatchController.class).searchMatchHistory(null, null)).withRel("history"),
                linkTo(methodOn(MatchController.class).searchMatchHistoryItem(entity.getId(), null)).withSelfRel()
        );

        return matchSearchModel;
    }

}
