package com.esgi.jee.basket.team.use_cases;

import com.esgi.jee.basket.team.domain.TeamRepository;
import com.esgi.jee.basket.team.domain.model.Team;
import org.springframework.stereotype.Service;

@Service
public class CreateTeam {

    private final TeamRepository teamRepository;

    public CreateTeam(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team execute(Team team){

        return teamRepository.save(team);
    }
}
