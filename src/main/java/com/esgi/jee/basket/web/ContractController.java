package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.ContractNotFoundException;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import com.esgi.jee.basket.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ContractRepository contractRepository;

    private final PlayerModelAssembler playerModelAssembler;
    private final ContractModelAssembler contractModelAssembler;

    @GetMapping(path = "/teams/{teamId}/contract")
    public CollectionModel<PlayerModel> getPlayerInTeam(@PathVariable Long teamId){

        if(!teamRepository.existsById(teamId))

            throw new TeamNotFoundException(teamId);

        List<PlayerModel> allPlayer = contractRepository.findPlayerInTeam(teamId).stream()
                .map(playerModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(allPlayer,
                linkTo(methodOn(ContractController.class).getPlayerInTeam(teamId)).withSelfRel());
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

        return contractModelAssembler.toModel(contractRepository.findById(id).orElseThrow(() -> new ContractNotFoundException(id)));
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
