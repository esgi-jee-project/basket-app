package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.InvalidFieldException;
import com.esgi.jee.basket.web.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Component
@Service
public class MatchService {

    private TeamRepository teamRepository;

    public MatchService(TeamRepository teamRepository) {

        this.teamRepository = teamRepository;
    }

    public Match createGame (MatchCreateModel match) {

        if(match.getIdTeamOpponent().equals(match.getIdTeamLocal())) throw new InvalidFieldException("The same team can't be in a match");

        Team teamLocal = teamRepository.findById(match.getIdTeamLocal())
                                        .orElseThrow(() -> new InvalidFieldException("Team id " + match.getIdTeamLocal() + " Not found"));

        Team teamOpponent = teamRepository.findById(match.getIdTeamOpponent())
                                        .orElseThrow(() -> new InvalidFieldException("Team id " + match.getIdTeamOpponent() + " Not found"));

        return Match.builder()
                    .date(match.getDate())
                    .place(match.getPlace())
                    .idNameLocal(teamLocal)
                    .idNameOpponent(teamOpponent)
                    .scoreLocal(0)
                    .scoreOpponent(0)
                    .playerTeamLocal(new ArrayList<>())
                    .playerTeamOpponent(new ArrayList<>())
                    .build();
    }
}
