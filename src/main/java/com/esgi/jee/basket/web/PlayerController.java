package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.PlayerRepository;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository repository;
    private final TeamRepository teamRepository;
    private final PagedResourcesAssembler<Player> pagedResourcesAssembler;
    private final PlayerModelAssembler playerModelAssembler;

    @GetMapping(path = "/players")
    public PagedModel<PlayerModel> getAll(Pageable pageable) {

        /*return repository.findAll().stream()
                .map(playerModelAssembler::toModel)
                .collect(Collectors.toList());*/

        Page<Player> players = repository.findAll(pageable);

        return pagedResourcesAssembler.toModel(players, playerModelAssembler);
    }

    @PostMapping(path = "/players")
    public ResponseEntity<PlayerModel> create(@RequestBody Player player){

        PlayerModel playerModel = playerModelAssembler.toModel(repository.save(player));

        return ResponseEntity.created(playerModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(playerModel);
    }

    @GetMapping(path = "/players/{id}")
    public PlayerModel getOne(@PathVariable Long id){

        return playerModelAssembler.toModel(repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id)));
    }

    @PutMapping(path = "/players/{id}")
    public PlayerModel updateOne(@PathVariable Long id, @RequestBody Player updatePlayer){

        Player findPlayer = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        findPlayer.setFirstname(updatePlayer.getFirstname());
        findPlayer.setLastname(updatePlayer.getLastname());

        return playerModelAssembler.toModel(repository.save(findPlayer));
    }
}
