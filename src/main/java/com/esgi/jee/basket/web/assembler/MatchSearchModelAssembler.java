package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchSearch;
import com.esgi.jee.basket.web.model.MatchModel;
import com.esgi.jee.basket.web.model.MatchSearchModel;
import lombok.AllArgsConstructor;
import org.hibernate.criterion.MatchMode;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MatchSearchModelAssembler implements RepresentationModelAssembler<MatchSearch, MatchSearchModel> {
    private MatchModelAssembler matchModelAssembler;
    public MatchSearchModel toModel(MatchSearch entity){

        MatchSearchModel model = MatchSearchModel.builder().research(entity.getResearch())
                                                            .ipAddress(entity.getIpAddress())
                                                            .totalResult(entity.getTotalResult())
                                                            .result(entity.getResult().stream().map(matchModelAssembler::toModel).collect(Collectors.toList()))
                                                            .build();

        return model;
    }
}
