package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.web.model.TeamModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Page<Team> findAll(Pageable pageable){

        return teamRepository.findAll(pageable);
    }

    public Team create(TeamModel data){

        Team team = Team.builder()
                            .name(data.getName())
                            .country(data.getCountry())
                        .build();

        return teamRepository.save(team);
    }

    public Optional<Team> findById(Long id){

        return teamRepository.findById(id);
    }

    public Optional<Team> update(Long id, TeamModel data){

        Optional<Team> find = teamRepository.findById(id);

        return find.map(team -> {
            team.setName(data.getName() != null ? data.getName() : team.getName());
            team.setCountry(data.getCountry() != null ? data.getCountry() : team.getCountry());

            return teamRepository.save(team);
        });
    }
}
