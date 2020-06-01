package com.esgi.jee.basket.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query(value = "select c " +
                "from Contract c " +
                "left join fetch c.player " +
                "where c.team.id = ?1 " +
                "order by c.startDate desc",
            countQuery = "select count(contract) from Contract contract where contract.team.id = ?1"
    )
    Page<Contract> findTeamContract(Long idTeam, Pageable pageable);

    @Query(value="select c " +
            "from Contract c " +
            "left join fetch c.team " +
            "where c.player.id = ?1 " +
            "order by c.startDate desc",
            countQuery = "select count(contract) from Contract contract where contract.player.id = ?1"
    )
    Page<Contract> findPlayerContract(Long idPlayer, Pageable pageable);

    @Override
    @Query("select c " +
            "from Contract c " +
            "left join fetch c.team " +
            "left join fetch c.player " +
            "where c.id = ?1"
    )
    Optional<Contract> findById(Long aLong);
}
