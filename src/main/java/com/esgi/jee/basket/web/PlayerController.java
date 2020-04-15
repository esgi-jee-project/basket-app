package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.PlayerRepository;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.exception.PlayerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository repository;
    private final TeamRepository teamRepository;

    @GetMapping(path = "/players")
    public List<Player> getAll() {

        return repository.findAll();
    }

    @PostMapping(path = "/players")
    public Player create(@RequestBody Player player){
        return repository.save(player);
    }

    @GetMapping(path = "/players/{id}")
    public Player getOne(@PathVariable Long id){

        return repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @PutMapping(path = "/players/{id}")
    public Player updateOne(@PathVariable Long id, @RequestBody Player updatePlayer){

        Player findPlayer = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        findPlayer.setFirstname(updatePlayer.getFirstname());
        findPlayer.setLastname(updatePlayer.getLastname());

        return repository.save(findPlayer);
    }
}
