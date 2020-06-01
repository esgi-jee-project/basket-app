package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.exception.TeamNotFoundException;
import com.esgi.jee.basket.web.assembler.TeamModelAssembler;
import com.esgi.jee.basket.web.model.TeamModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository repository;
    private final PagedResourcesAssembler<Team> pagedResourcesAssembler;
    private final TeamModelAssembler modelAssembler;

    @GetMapping
    public PagedModel<TeamModel> getAll(Pageable pageable) {

        Page<Team> teams = repository.findAll(pageable);

        return pagedResourcesAssembler.toModel(teams, modelAssembler);
    }

    @PostMapping()
    @PreAuthorize("hasRole('LEAGUE_ADMINISTRATOR')")
    public ResponseEntity<TeamModel> create(@RequestBody @Valid Team newTeam){

        Team createdTeam = repository.save(newTeam);

        TeamModel entityModel = modelAssembler.toModel(createdTeam);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping(path="/{id}")
    public TeamModel getById(@PathVariable Long id){

        Team team = repository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));

        return modelAssembler.toModel(team);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LEAGUE_ADMINISTRATOR')")
    public TeamModel updateTeam(@RequestBody Team newTeam, @PathVariable Long id){

        return modelAssembler.toModel(repository.findById(id).map(team -> {
            team.setCountry(newTeam.getCountry());
            team.setName(newTeam.getName());

            return repository.save(team);
        }).orElseThrow(() -> new TeamNotFoundException(id)));
    }
}
