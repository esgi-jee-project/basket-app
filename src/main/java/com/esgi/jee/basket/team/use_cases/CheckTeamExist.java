package com.esgi.jee.basket.team.use_cases;

import com.esgi.jee.basket.team.domain.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckTeamExist {

    private final TeamRepository teamRepository;

    public CheckTeamExist(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public boolean execute(Long id){

        return teamRepository.existsById(id);
    }
}
