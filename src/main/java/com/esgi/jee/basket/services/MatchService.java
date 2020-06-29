package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.InvalidFieldException;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import com.esgi.jee.basket.web.model.MatchCreateModel;
import com.esgi.jee.basket.web.model.MatchSetScoreModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Component
@Service
public class MatchService {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public MatchService(TeamRepository teamRepository, MatchRepository matchRepository) {

        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
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
    public Match setScore(MatchSetScoreModel score, long idMatch) {
        Match match = matchRepository.findById(idMatch).orElseThrow(() -> new MatchNotFoundException(idMatch));
        match.setScoreLocal(score.getScoreLocalTeam());
        match.setScoreOpponent(score.getScoreOpponentTeam());
        matchRepository.save(match);
        return match;
    }
}
