package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Component
@Service
public class MatchService {

    private TeamRepository teamRepository;

    public MatchService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Match createMatch(Match match){
        List<String> teamsName = teamRepository.findAll()
                                            .stream()
                                            .map(Team::getName)
                                            .collect(Collectors.toList());

        Random random = new Random();

        String name = teamsName.get(random.nextInt(teamsName.size()));
        System.out.println("Teams : " + name);

        Match m = new Match();
        match.setDate(match.getDate());
        match.setNameLocal(name);
        match.setNameOpponent(name);
        System.out.println("Created project with name : " + match.getId());
        return m;
    }
}
