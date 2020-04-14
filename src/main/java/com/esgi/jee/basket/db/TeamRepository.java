package com.esgi.jee.basket.db;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {

    List<Team> findAll();
}
