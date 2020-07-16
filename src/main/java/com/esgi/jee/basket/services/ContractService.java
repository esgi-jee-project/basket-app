package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.db.ContractRepository;
import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.web.model.ContractModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public Page<Contract> findTeamContract(Long idTeam, Pageable pageable){

        return contractRepository.findTeamContract(idTeam, pageable);
    }

    public Page<Contract> findPlayerContract(Long idPlayer, Pageable pageable){

        return contractRepository.findPlayerContract(idPlayer, pageable);
    }

    public Contract create(ContractModel data, Player player, HibernateTeam team){

        Contract contract = Contract.builder()
                    .startDate(data.getStartDate())
                    .endDate(data.getEndDate())
                    .player(player)
                    .team(team)
                .build();

        return contractRepository.save(contract);
    }

    public Optional<Contract> findById(Long id){

        return contractRepository.findById(id);
    }

    public Optional<Contract> update(Long id, ContractModel data){

        Optional<Contract> find = contractRepository.findById(id);

        return find.map(contract -> {
            contract.setStartDate(data.getStartDate() != null ? data.getStartDate() : contract.getStartDate());
            contract.setEndDate(data.getEndDate() != null ? data.getEndDate() : contract.getEndDate());

            return contractRepository.save(contract);
        });
    }
}
