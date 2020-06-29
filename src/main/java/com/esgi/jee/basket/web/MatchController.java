package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchRepository;
import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import com.esgi.jee.basket.services.MatchService;
import com.esgi.jee.basket.web.assembler.MatchModelAssembler;
import com.esgi.jee.basket.web.model.MatchCreateModel;
import com.esgi.jee.basket.web.model.MatchModel;
import com.esgi.jee.basket.web.model.PlayerInsertionModel;
import com.esgi.jee.basket.web.model.PlayerModel;
import com.esgi.jee.basket.web.model.MatchSetScoreModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;
    private final PagedResourcesAssembler<Match> pagedResourcesAssembler;
    private final MatchModelAssembler modelAssembler;
    private final TeamRepository teamRepository;
    private final MatchService matchService;

    @GetMapping(path = "/matchs")
    public PagedModel<MatchModel> getAll(Pageable pageable) {
        Page<Match> matchPage = matchRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(matchPage, modelAssembler);
    }

    @PostMapping(path = "/game")
    public ResponseEntity<?> newGame(@RequestBody @Valid MatchCreateModel match){
        try {
            Match m = matchService.createGame(match);
            modelAssembler.toModel(m);
            matchRepository.save(m);
            return new ResponseEntity<>(m, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/match/{id}")
    public Match getOne(@PathVariable Long id){

        return matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException(id));
    }
    @PutMapping(path = "/matchs/{id}/score")
    public Match setScore(@RequestBody @Valid MatchSetScoreModel match, @PathVariable Long id){
        Match m = matchService.setScore(match,id);
        modelAssembler.toModel(m);
        return m;

    }

    @PutMapping(path = "/match/{id}/teamLocal/{idTeamLocal}")
    public List<PlayerModel> addLocalPlayers(@RequestBody @Valid List<PlayerInsertionModel> players, @PathVariable Long id, @PathVariable Long idTeamLocal) {

        matchService.addPlayersLocal(players, id, idTeamLocal);

        return new ArrayList<>();
    }
}
