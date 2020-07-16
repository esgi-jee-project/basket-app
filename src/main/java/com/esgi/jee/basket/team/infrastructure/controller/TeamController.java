package com.esgi.jee.basket.team.infrastructure.controller;

import com.esgi.jee.basket.exception.TeamNotFoundException;
import com.esgi.jee.basket.team.domain.model.Team;
import com.esgi.jee.basket.team.use_cases.CreateTeam;
import com.esgi.jee.basket.team.use_cases.FindTeam;
import com.esgi.jee.basket.team.use_cases.FindTeamById;
import com.esgi.jee.basket.team.use_cases.UpdateTeam;
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
@RequestMapping(path = "/teams")
@RequiredArgsConstructor
public class TeamController {

    private final FindTeam findTeam;
    private final CreateTeam createTeam;
    private final FindTeamById findTeamById;
    private final UpdateTeam updateTeam;
    private final PagedResourcesAssembler<Team> pagedResourcesAssembler;
    private final TeamModelAssembler modelAssembler;

    @GetMapping
    public PagedModel<TeamModel> getAll(Pageable pageable) {

        Page<Team> teams = findTeam.execute(pageable);

        return pagedResourcesAssembler.toModel(teams, modelAssembler);
    }

    @PostMapping()
//    @PreAuthorize("hasRole('LEAGUE_ADMINISTRATOR')")
    public ResponseEntity<TeamModel> create(@RequestBody @Valid TeamModel newTeam){

        Team createdTeam = createTeam.execute(modelAssembler.toTeam(newTeam));

        TeamModel entityModel = modelAssembler.toModel(createdTeam);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping(path="/{id}")
    public TeamModel getById(@PathVariable Long id){

        Team team = findTeamById.execute(id).orElseThrow(() -> new TeamNotFoundException(id));

        return modelAssembler.toModel(team);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('LEAGUE_ADMINISTRATOR')")
    public TeamModel updateTeam(@RequestBody TeamModel newTeam, @PathVariable Long id){

        return modelAssembler.toModel(updateTeam.execute(id, modelAssembler.toTeam(newTeam)).orElseThrow(() -> new TeamNotFoundException(id)));
    }
}
