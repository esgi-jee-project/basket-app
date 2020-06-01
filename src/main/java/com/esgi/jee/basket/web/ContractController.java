package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.ContractNotFoundException;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import com.esgi.jee.basket.exception.TeamNotFoundException;
import com.esgi.jee.basket.web.assembler.ContractModelAssembler;
import com.esgi.jee.basket.web.assembler.ContractWithPlayerModelAssembler;
import com.esgi.jee.basket.web.assembler.ContractWithTeamModelAssembler;
import com.esgi.jee.basket.web.model.ContractModel;
import com.esgi.jee.basket.web.model.ContractWithPlayerModel;
import com.esgi.jee.basket.web.model.ContractWithTeamModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ContractRepository contractRepository;

    private final ContractModelAssembler contractModelAssembler;
    private final ContractWithPlayerModelAssembler contractWithPlayerModelAssembler;
    private final ContractWithTeamModelAssembler contractWithTeamModelAssembler;

    private final PagedResourcesAssembler<Contract> contractPagedResourcesAssembler;

    @GetMapping(path = "/teams/{teamId}/contract")
    public PagedModel<ContractWithPlayerModel> getPlayerInTeam(@PathVariable Long teamId, Pageable pageable){

        if(!teamRepository.existsById(teamId))

            throw new TeamNotFoundException(teamId);

        Page<Contract> allContract = contractRepository.findTeamContract(teamId, pageable);

        return contractPagedResourcesAssembler.toModel(allContract, contractWithPlayerModelAssembler);
    }

    @GetMapping(path = "/players/{playerId}/contract")
    public PagedModel<ContractWithTeamModel> getPlayerContract(@PathVariable Long playerId, Pageable pageable){

        Page<Contract> allContract = contractRepository.findPlayerContract(playerId, pageable);

        return contractPagedResourcesAssembler.toModel(allContract, contractWithTeamModelAssembler);
    }

    @PostMapping(path = "/teams/{teamId}/contract/{playerId}")
    public ResponseEntity<ContractModel> createContract(@PathVariable Long teamId, @PathVariable Long playerId, @RequestBody @Valid Contract contract){

        Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        contract.setPlayer(player);
        contract.setTeam(team);

        Contract newContract = contractRepository.save(contract);
        ContractModel contractModel = contractModelAssembler.toModel(newContract);

        // TODO check if a contract already exist before link

        if((contract.getEndDate() == null || contract.getEndDate().isAfter(LocalDate.now())) && contract.getStartDate().isBefore(LocalDate.now())){

            player.setCurrentContract(newContract);
            playerRepository.save(player);
        }

        return ResponseEntity
                .created(contractModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(contractModel);
    }

    @GetMapping(path = "/contract/{id}")
    public ContractModel selectOne(@PathVariable Long id){

        Contract contract = contractRepository.findById(id).orElseThrow(() -> new ContractNotFoundException(id));

        return contractModelAssembler.toModel(contract);
    }

    @PutMapping(path = "/contract/{id}")
    public ContractModel update(@PathVariable Long id, @RequestBody @Valid Contract updateContract){

        return contractModelAssembler.toModel(contractRepository.findById(id).map(contract -> {
            // TODO think if we allow team and player modification or delete
            //contract.setTeam(updateContract.getTeam());
            //contract.setPlayer(updateContract.getPlayer());
            contract.setStartDate(updateContract.getStartDate());
            contract.setEndDate(updateContract.getEndDate());

            return contract;
        }).orElseThrow(() -> new ContractNotFoundException(id)));
    }
}
