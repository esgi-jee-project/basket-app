package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Service
public class MatchService {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;
    private ContractRepository contractRepository;

    public MatchService(TeamRepository teamRepository, MatchRepository matchRepository, ContractRepository contractRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.contractRepository = contractRepository;
    }

    public Match createGame (Match match) {
        List<Team> teamsName = teamRepository.findAll();

        Match m = new Match();
        m.setDate(match.getDate());
        m.setPlace(match.getPlace());
        for( Team team : teamsName ) {
            System.out.println("Local name boucle");
            if(match.getNameLocal().equals(team.getName())) {
                System.out.println("Name local OK : " + match.getNameLocal());
                List<Player> playersLocal = contractRepository.findPlayerInTeam(team.getId());
                m.setNameLocal(match.getNameLocal());
                m.setPlayerTeamLocal(playersLocal);
            }
        }
        for (Team team : teamsName) {
            System.out.println("Opponent name boucle");
            if(match.getNameOpponent().equals(team.getName()) && !match.getNameOpponent().equals(match.getNameLocal())) {
                System.out.println("Opponent name OK : " + match.getNameOpponent());
                List<Player> playersOpponent = contractRepository.findPlayerInTeam(team.getId());
                m.setNameOpponent(match.getNameOpponent());
                m.setPlayerTeamOpponent(playersOpponent);
            }
        }
        m.setScoreLocal(40 + (int)(Math.random() * ((200 - 40) + 1)));
        m.setScoreOpponent(40 + (int)(Math.random() * ((200 - 40) + 1)));
        return  m;
    }

    public Match createMatch(Match match){
        List<Team> teamsName = teamRepository.findAll();


        Random random = new Random();

        Team teamLocal = teamsName.get(random.nextInt(teamsName.size()));
        teamsName.remove(teamLocal);
        Team teamOpponent = teamsName.get(random.nextInt(teamsName.size()));

        List<Player> playersLocal = contractRepository.findPlayerInTeam(teamLocal.getId());
        List<Player> playersOpponent = contractRepository.findPlayerInTeam(teamOpponent.getId());

        System.out.println("Team : " + teamLocal);
        System.out.println("Team Adverse : " + teamOpponent);

        String placeLocal = teamLocal.getPlace();

        String placeOpponent = teamOpponent.getPlace();

        List<String> places = Arrays.asList(placeLocal, placeOpponent);

        String place = places.get(random.nextInt(places.size()));


        System.out.println("Lieux : " + place);

        Match m = new Match();
        m.setDate(match.getDate());
        m.setPlace(place);
        m.setNameLocal(teamLocal.getName());
        m.setNameOpponent(teamOpponent.getName());
        m.setScoreLocal(40 + (int)(Math.random() * ((200 - 40) + 1)));
        m.setScoreOpponent(40 + (int)(Math.random() * ((200 - 40) + 1)));
        m.setPlayerTeamLocal(playersLocal);
        m.setPlayerTeamOpponent(playersOpponent);

        System.out.println("Created match with ID : " + m.getId());
        return m;
    }
}
