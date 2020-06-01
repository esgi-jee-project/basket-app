package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import com.esgi.jee.basket.web.assembler.PlayerModelAssembler;
import com.esgi.jee.basket.web.assembler.PlayerWithContractModelAssembler;
import com.esgi.jee.basket.web.model.PlayerModel;
import com.esgi.jee.basket.web.model.PlayerWithContractModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository repository;

    private final PagedResourcesAssembler<Player> pagedResourcesAssembler;

    private final PlayerModelAssembler playerModelAssembler;
    private final PlayerWithContractModelAssembler playerWithContractModelAssembler;

    @GetMapping(path = "/players")
    public PagedModel<PlayerModel> getAll(Pageable pageable) {

        Page<Player> players = repository.findAll(pageable);

        return pagedResourcesAssembler.toModel(players, playerModelAssembler);
    }

    @PostMapping(path = "/players")
    public ResponseEntity<PlayerModel> create(@RequestBody @Valid Player player){

        PlayerModel playerModel = playerModelAssembler.toModel(repository.save(player));

        return ResponseEntity.created(playerModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(playerModel);
    }

    @GetMapping(path = "/players/{id}")
    public PlayerModel getOne(@PathVariable Long id){

        Player player = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));

        return playerModelAssembler.toModel(player);
    }

    @PutMapping(path = "/players/{id}")
    public PlayerModel updateOne(@PathVariable Long id, @RequestBody Player updatePlayer){

        Player findPlayer = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        findPlayer.setFirstname(updatePlayer.getFirstname());
        findPlayer.setLastname(updatePlayer.getLastname());

        return playerModelAssembler.toModel(repository.save(findPlayer));
    }
}
