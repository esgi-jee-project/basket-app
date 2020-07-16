package com.esgi.jee.basket.services;

import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.team.infrastructure.dao.repository.SpringTeamRepository;
import com.esgi.jee.basket.team.infrastructure.controller.TeamModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final SpringTeamRepository teamRepository;

    public Page<HibernateTeam> findAll(Pageable pageable){

        return teamRepository.findAll(pageable);
    }

    public boolean existsById(long id) {

        return teamRepository.existsById(id);
    }

    public HibernateTeam create(TeamModel data){

        HibernateTeam team = HibernateTeam.builder()
                            .name(data.getName())
                            .country(data.getCountry())
                            .place(data.getPlace())
                        .build();

        return teamRepository.save(team);
    }

    public Optional<HibernateTeam> findById(Long id){

        return teamRepository.findById(id);
    }

    public Optional<HibernateTeam> update(Long id, TeamModel data){

        Optional<HibernateTeam> find = teamRepository.findById(id);

        return find.map(team -> {
            team.setName(data.getName() != null ? data.getName() : team.getName());
            team.setCountry(data.getCountry() != null ? data.getCountry() : team.getCountry());

            return teamRepository.save(team);
        });
    }
}
