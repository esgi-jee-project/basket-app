package com.esgi.jee.basket.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

    @Override
    @Query("from Player player left join Contract contract on contract.id=player.currentContract.id")
    List<Player> findAll();

    @Override
    @Query(value="select * from player left outer join contract on player.current_contract_id=contract.id left outer join team on contract.team_id = team.id",
            countQuery = "select count(*) from player",
            nativeQuery = true
    )
    Page<Player> findAll(Pageable pageable);
}
