package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.exception.ContractNotFoundException;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import com.esgi.jee.basket.exception.TeamNotFoundException;
import com.esgi.jee.basket.services.ContractService;
import com.esgi.jee.basket.services.PlayerService;
import com.esgi.jee.basket.services.TeamService;
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

    private final PlayerService playerService;
    private final TeamService teamService;
    private final ContractService contractService;

    private final ContractModelAssembler contractModelAssembler;
    private final ContractWithPlayerModelAssembler contractWithPlayerModelAssembler;
    private final ContractWithTeamModelAssembler contractWithTeamModelAssembler;

    private final PagedResourcesAssembler<Contract> contractPagedResourcesAssembler;

    @GetMapping(path = "/teams/{teamId}/contract")
    public PagedModel<ContractWithPlayerModel> getTeamContract(@PathVariable Long teamId, Pageable pageable){

        if(!teamService.existsById(teamId))

            throw new TeamNotFoundException(teamId);

        Page<Contract> allContract = contractService.findTeamContract(teamId, pageable);

        return contractPagedResourcesAssembler.toModel(allContract, contractWithPlayerModelAssembler);
    }

    @GetMapping(path = "/players/{playerId}/contract")
    public PagedModel<ContractWithTeamModel> getPlayerContract(@PathVariable Long playerId, Pageable pageable){

        if(!playerService.existsById(playerId))

            throw new PlayerNotFoundException(playerId);

        Page<Contract> allContract = contractService.findPlayerContract(playerId, pageable);

        return contractPagedResourcesAssembler.toModel(allContract, contractWithTeamModelAssembler);
    }

    @PostMapping(path = "/teams/{teamId}/contract/{playerId}")
    public ResponseEntity<ContractModel> createContract(@PathVariable Long teamId, @PathVariable Long playerId, @RequestBody @Valid ContractModel contract){

        Player player = playerService.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        HibernateTeam team = teamService.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        Contract newContract = contractService.create(contract, player, team);
        ContractModel contractModel = contractModelAssembler.toModel(newContract);

        // TODO check if a contract already exist before link

        boolean isCurrentContract = (contract.getEndDate() == null || contract.getEndDate().isAfter(LocalDate.now()))
                                        && contract.getStartDate().isBefore(LocalDate.now());

        if(isCurrentContract){
            playerService.updateCurrentContract(playerId, newContract).orElseThrow(() -> new PlayerNotFoundException(playerId));
        }

        return ResponseEntity
                .created(contractModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(contractModel);
    }

    @GetMapping(path = "/contract/{id}")
    public ContractModel selectOne(@PathVariable Long id){

        Contract contract = contractService.findById(id).orElseThrow(() -> new ContractNotFoundException(id));

        return contractModelAssembler.toModel(contract);
    }

    @PutMapping(path = "/contract/{id}")
    public ContractModel update(@PathVariable Long id, @RequestBody @Valid ContractModel updateContract){

        return contractModelAssembler.toModel(contractService.update(id, updateContract).orElseThrow(() -> new ContractNotFoundException(id)));
    }
}
