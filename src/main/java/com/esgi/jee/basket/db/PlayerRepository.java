package com.esgi.jee.basket.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Override
    @Query(value="select p " +
                "from Player p " +
                "order by p.firstname",
            countQuery = "select count(p) from Player p"
    )
    Page<Player> findAll(Pageable pageable);

    @Query("from Player p left join p.currentContract c where p.id in (?1) and c.team.id = ?2")
    List<Player> findAllByIdCheckTeam(List<Long> player, Long idTeam);
}
