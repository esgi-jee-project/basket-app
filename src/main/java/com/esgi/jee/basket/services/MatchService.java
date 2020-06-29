package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.InvalidFieldException;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import com.esgi.jee.basket.web.assembler.PlayerMatchModelAssembler;
import com.esgi.jee.basket.web.model.MatchCreateModel;
import com.esgi.jee.basket.web.model.PlayerInsertionModel;
import com.esgi.jee.basket.web.model.PlayerModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Service
public class MatchService {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;
    private ContractRepository contractRepository;
    private PlayerMatchModelAssembler playerMatchModelAssembler;

    public MatchService(TeamRepository teamRepository, MatchRepository matchRepository, ContractRepository contractRepository, PlayerMatchModelAssembler playerMatchModelAssembler) {

        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.contractRepository = contractRepository;
        this.playerMatchModelAssembler = playerMatchModelAssembler;
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
    public List<Player> addPlayersLocal (List<PlayerInsertionModel> players, Long idMatch, Long idTeamLocal) {
        Match match = matchRepository.findById(idMatch).orElseThrow(() -> new MatchNotFoundException(idMatch));

//        for (PlayerModel player : players) {
//            match.addPlayerTeamLocal(player);
//        }
        // TODO : Véréfier que le joueur est bien dans l'équipe

        List<Player> playersLocal = players.stream()
                                                .map(player -> Player.builder()
                                                    .id(player.getId())
                                                    .build())
                                                .collect(Collectors.toList());



        match.setPlayerTeamLocal(playersLocal);

        matchRepository.save(match);
        return null;
    }
}
