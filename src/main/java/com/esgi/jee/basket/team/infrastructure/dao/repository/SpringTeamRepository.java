package com.esgi.jee.basket.team.infrastructure.dao.repository;

import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SpringTeamRepository extends PagingAndSortingRepository<HibernateTeam, Long> {

    List<HibernateTeam> findAll();
}
