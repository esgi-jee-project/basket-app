package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.InvalidFieldException;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.team.infrastructure.dao.repository.SpringTeamRepository;
import com.esgi.jee.basket.web.model.MatchCreateModel;
import com.esgi.jee.basket.web.model.MatchSetScoreModel;
import com.esgi.jee.basket.web.model.PlayerInsertionModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private SpringTeamRepository teamRepository;
    private MatchRepository matchRepository;
    private PlayerRepository playerRepository;

    public Match createGame (MatchCreateModel match) {

        if(match.getIdTeamOpponent().equals(match.getIdTeamLocal())) throw new InvalidFieldException("The same team can't be in a match");

        HibernateTeam teamLocal = teamRepository.findById(match.getIdTeamLocal())
                                        .orElseThrow(() -> new InvalidFieldException("Team id " + match.getIdTeamLocal() + " Not found"));

        HibernateTeam teamOpponent = teamRepository.findById(match.getIdTeamOpponent())
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
    private List<Player> mapping (List<Player> players) {
        return players.stream()
                .map(player -> Player.builder()
                        .id(player.getId())
                        .firstname(player.getFirstname())// first name
                        .lastname(player.getLastname())// last name
                        .build())
                .collect(Collectors.toList());
    }

    public Match addLocalPlayers (List<PlayerInsertionModel> players, String idMatch, Long idTeamLocal) {

        Match match = matchRepository.findById(idMatch).orElseThrow(() -> new InvalidFieldException("Match " + idMatch + "not found"));
        List<Player> findPlayer = playerRepository.findAllById(players.stream().map(PlayerInsertionModel::getId).collect(Collectors.toList()));

        if (findPlayer.size() != players.size()){
            throw new InvalidFieldException("Both list doesn't have the same size");
        }
        List<Player> addingPlayers = mapping(findPlayer);
        match.setPlayerTeamLocal(addingPlayers);
        matchRepository.save(match);
        return match;
    }
    public Match addOpponentPlayers (List<PlayerInsertionModel> players, String idMatch, Long idTeamOpponent) {

        Match match = matchRepository.findById(idMatch).orElseThrow(() -> new InvalidFieldException("Match " + idMatch + "not found"));
        List<Player> findPlayer = playerRepository.findAllByIdCheckTeam(players.stream().map(PlayerInsertionModel::getId).collect(Collectors.toList()), idTeamOpponent);

        System.out.println("FIND ALL");
        System.out.println(findPlayer);

        if (findPlayer.size() != players.size()){
            throw new InvalidFieldException("Both list doesn't have the same size");
        }
        List<Player> addingPlayers = mapping(findPlayer);
        System.out.println("adding player");
        System.out.println(addingPlayers);
        match.setPlayerTeamOpponent(addingPlayers);

        System.out.println("match");
        System.out.println(match.getPlayerTeamOpponent());

        matchRepository.save(match);
        return match;
    }
    public Match setScore(MatchSetScoreModel score, String idMatch) {
        Match match = matchRepository.findById(idMatch).orElseThrow(() -> new MatchNotFoundException(idMatch));
        match.setScoreLocal(score.getScoreLocalTeam());
        match.setScoreOpponent(score.getScoreOpponentTeam());
        matchRepository.save(match);
        return match;
    }

    public Page<Match> getAll (Pageable pageable) {
        return matchRepository.findAll(pageable);
    }
    public Match getOne (String id) {
        return matchRepository.findById(id).orElseThrow(() -> new  InvalidFieldException ("Match " + id + " not found "));
    }
}
