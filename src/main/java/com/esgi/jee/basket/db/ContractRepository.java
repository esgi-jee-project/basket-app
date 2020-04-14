package com.esgi.jee.basket.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("from Player player left join Contract c on c.id = player.currentContract.id where player.id in (select contract.player.id from Contract contract where contract.team.id = ?1)")
    List<Player> findPlayerInTeam(Long idTeam);
}
