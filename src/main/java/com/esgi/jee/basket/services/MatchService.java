package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.InvalidFieldException;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import com.esgi.jee.basket.web.assembler.PlayerMatchModelAssembler;
import com.esgi.jee.basket.web.model.MatchCreateModel;
import com.esgi.jee.basket.web.model.PlayerInsertionModel;
import com.esgi.jee.basket.web.model.MatchSetScoreModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Service
@AllArgsConstructor
public class MatchService {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;
    private PlayerRepository playerRepository;

    public PlayerMatchModelAssembler getPlayerMatchModelAssembler() {
        return playerMatchModelAssembler;
    }

    private ContractRepository contractRepository;
    private PlayerMatchModelAssembler playerMatchModelAssembler;

    public Match createGame (MatchCreateModel match) {

        if(match.getIdTeamOpponent().equals(match.getIdTeamLocal())) throw new InvalidFieldException("The same team can't be in a match");

        Team teamLocal = teamRepository.findById(match.getIdTeamLocal())
                                        .orElseThrow(() -> new InvalidFieldException("Team id " + match.getIdTeamLocal() + " Not found"));

        Team teamOpponent = teamRepository.findById(match.getIdTeamOpponent())
                                        .orElseThrow(() -> new InvalidFieldException("Team id " + match.getIdTeamOpponent() + " Not found"));

        Match newMatch = Match.builder()
                            .date(match.getDate().format(DateTimeFormatter.ISO_DATE))
                            .place(match.getPlace())
                            .idNameLocal(teamLocal)
                            .idNameOpponent(teamOpponent)
                            .scoreLocal(0)
                            .scoreOpponent(0)
                            .playerTeamLocal(new ArrayList<>())
                            .playerTeamOpponent(new ArrayList<>())
                        .build();

        return matchRepository.save(newMatch);
    }

    public List<Player> addPlayersLocal (List<PlayerInsertionModel> players, String idMatch, Long idTeamLocal) {
        Match match = matchRepository.findById(idMatch).orElseThrow(() -> new MatchNotFoundException(idMatch));

//        List<Player> findPlayer = playerRepository.findAllById(players.stream().map(PlayerInsertionModel::getId).collect(Collectors.toList()));
//
//        findPlayer.size() != players.size(){
//            erreur
//        }

        // TODO : Véréfier que le joueur est bien dans l'équipe

        List<Player> playersLocal = players.stream()
                                                .map(player -> Player.builder()
                                                    .id(player.getId())
                                                    // first name
                                                    // last name
                                                    .build())
                                                .collect(Collectors.toList());



        match.setPlayerTeamLocal(playersLocal);

        matchRepository.save(match);
        return null;
    }
    public Match setScore(MatchSetScoreModel score, String idMatch) {
        Match match = matchRepository.findById(idMatch).orElseThrow(() -> new MatchNotFoundException(idMatch));
        match.setScoreLocal(score.getScoreLocalTeam());
        match.setScoreOpponent(score.getScoreOpponentTeam());
        matchRepository.save(match);
        return match;
    }
}
