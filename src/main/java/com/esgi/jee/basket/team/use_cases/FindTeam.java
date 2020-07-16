package com.esgi.jee.basket.team.use_cases;

import com.esgi.jee.basket.team.domain.TeamRepository;
import com.esgi.jee.basket.team.domain.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindTeam {

    private final TeamRepository teamRepository;

    public FindTeam(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Page<Team> execute(Pageable pageable){

        return teamRepository.findAll(pageable);
    }
}
