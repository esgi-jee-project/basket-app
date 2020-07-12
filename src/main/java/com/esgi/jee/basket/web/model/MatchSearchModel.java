package com.esgi.jee.basket.web.model;

import com.esgi.jee.basket.db.Match;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@Getter
public class MatchSearchModel extends RepresentationModel<MatchSearchModel> {

    private String research;

    private String ipAddress;

    private int totalResult;

    private List<MatchModel> result;
}
