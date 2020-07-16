package com.esgi.jee.basket.team.use_cases;

import com.esgi.jee.basket.team.domain.TeamRepository;
import com.esgi.jee.basket.team.domain.model.Team;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindTeamById {

    private final TeamRepository teamRepository;

    public FindTeamById(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> execute(Long id){

        return teamRepository.findById(id);
    }
}
