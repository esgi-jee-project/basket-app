package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import com.esgi.jee.basket.services.PlayerService;
import com.esgi.jee.basket.web.assembler.PlayerModelAssembler;
import com.esgi.jee.basket.web.model.PlayerModel;
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

    private final PlayerService playerService;
    private final PagedResourcesAssembler<Player> pagedResourcesAssembler;
    private final PlayerModelAssembler playerModelAssembler;

    @GetMapping(path = "/players")
    public PagedModel<PlayerModel> getAll(Pageable pageable) {

        Page<Player> players = playerService.findAll(pageable);

        return pagedResourcesAssembler.toModel(players, playerModelAssembler);
    }

    @PostMapping(path = "/players")
    public ResponseEntity<PlayerModel> create(@RequestBody @Valid PlayerModel player){

        Player createPlayer = playerService.create(player);
        PlayerModel playerModel = playerModelAssembler.toModel(createPlayer);

        return ResponseEntity.created(playerModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(playerModel);
    }

    @GetMapping(path = "/players/{id}")
    public PlayerModel getOne(@PathVariable Long id){

        Player player = playerService.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));

        return playerModelAssembler.toModel(player);
    }

    @PutMapping(path = "/players/{id}")
    public PlayerModel updateOne(@PathVariable Long id, @RequestBody PlayerModel toUpdate){

        Player updatedPlayer = playerService.update(id, toUpdate).orElseThrow(() -> new PlayerNotFoundException(id));

        return playerModelAssembler.toModel(updatedPlayer);
    }
}
