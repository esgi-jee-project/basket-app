package com.esgi.jee.basket.team.domain;

import com.esgi.jee.basket.team.domain.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TeamRepository {

    Page<Team> findAll(Pageable pageable);

    boolean existsById(Long id);

    Team save(Team team);

    Optional<Team> findById(Long id);

    Optional<Team> update(long id, Team team);
}
