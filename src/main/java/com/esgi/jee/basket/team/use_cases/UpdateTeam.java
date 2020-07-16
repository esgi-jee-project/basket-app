package com.esgi.jee.basket.team.use_cases;

import com.esgi.jee.basket.team.domain.TeamRepository;
import com.esgi.jee.basket.team.domain.model.Team;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;

import java.util.Optional;

public class UpdateTeam {

    private final TeamRepository teamRepository;

    public UpdateTeam(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> execute(Long id, Team data){

        return teamRepository.update(id, data);
    }
}