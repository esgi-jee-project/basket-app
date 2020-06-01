package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchRepository;
import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Component
@Service
public class MatchService {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public MatchService(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    public Match createGame (Match match) {
        List<Team> teamsName = teamRepository.findAll();

        Match m = new Match();
        m.setDate(match.getDate());
        m.setPlace(match.getPlace());
        for( Team team : teamsName ) {
            System.out.println("Local name boucle");
            if(team.getName() == match.getNameLocal()) {
                System.out.println("Name local OK");
                m.setNameLocal(match.getNameLocal());
            }
        }
        for (Team team : teamsName) {
            System.out.println("Opponent name boucle");
            if(team.getName() == match.getNameOpponent() && match.getNameOpponent() != match.getNameLocal()) {
                System.out.println("Opponent name OK");
                m.setNameOpponent(match.getNameOpponent());
            }
        }
        m.setScoreLocal(40 + (int)(Math.random() * ((200 - 40) + 1)));
        m.setScoreOpponent(40 + (int)(Math.random() * ((200 - 40) + 1)));
        System.out.println("Fonction OK");
        return  m;
    }

    public Match createMatch(Match match){
        List<Team> teamsName = teamRepository.findAll();

        Random random = new Random();

        Team teamLocal = teamsName.get(random.nextInt(teamsName.size()));
        teamsName.remove(teamLocal);
        Team teamOpponent = teamsName.get(random.nextInt(teamsName.size()));

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

        System.out.println("Created match with ID : " + m.getId());
        return m;
    }
}
