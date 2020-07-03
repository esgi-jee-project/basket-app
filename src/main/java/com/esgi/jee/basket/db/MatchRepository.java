package com.esgi.jee.basket.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MatchRepository extends JpaRepository <Match, Long> {

    @Query("from Match m join fetch m.idNameLocal join fetch m.idNameOpponent where m.id = ?1")
    public Optional<Match> findByIdWithTeam(Long id);
}
