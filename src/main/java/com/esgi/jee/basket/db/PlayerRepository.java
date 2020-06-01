package com.esgi.jee.basket.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Override
    @Query(value="select p " +
                "from Player p " +
                "order by p.firstname",
            countQuery = "select count(p) from Player p"
    )
    Page<Player> findAll(Pageable pageable);
}
