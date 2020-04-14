package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository repository;

    @GetMapping
    public List<Team> getAll() {

        return repository.findAll();
    }

    @PostMapping()
    public Team create(@RequestBody Team newTeam){

        return repository.save(newTeam);
    }

    @GetMapping(path="/{id}")
    public Team getById(@PathVariable Long id){

        return repository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Team updateTeam(@RequestBody Team newTeam, @PathVariable Long id){

        return repository.findById(id).map(team -> {
            team.setCountry(newTeam.getCountry());
            team.setName(newTeam.getName());

            return repository.save(team);
        }).orElseThrow(() -> new TeamNotFoundException(id));
    }
}
