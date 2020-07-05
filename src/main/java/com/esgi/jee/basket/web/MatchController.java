package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;
    private final PagedResourcesAssembler<Match> pagedResourcesAssembler;
    private final MatchModelAssembler modelAssembler;
    private final MatchService matchService;

    @GetMapping(path = "/matchs")
    public PagedModel<MatchModel> getAll(Pageable pageable) {
        Page<Match> matchPage = matchRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(matchPage, modelAssembler);
    }

    @PostMapping(path = "/match")
    public ResponseEntity<?> newGame(@RequestBody @Valid MatchCreateModel match){
        try {
            Match m = matchService.createGame(match);

            return new ResponseEntity<>(modelAssembler.toModel(m), HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/match/{id}")
    public MatchModel getOne(@PathVariable String id){

        Match findMatch = matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException(id));

        return modelAssembler.toModel(findMatch);
    }

    @PutMapping(path = "/match/{id}/score")
    public MatchModel setScore(@RequestBody @Valid MatchSetScoreModel match, @PathVariable String id){
        Match m = matchService.setScore(match,id);
        return modelAssembler.toModel(m);
    }

    @PutMapping(path = "/match/{id}/teamLocal/{idTeamLocal}")
    public MatchModel addLocalPlayers(@RequestBody @Valid List<PlayerInsertionModel> players, @PathVariable String id, @PathVariable Long idTeamLocal) {

        Match m = matchService.addLocalPlayers(players, id, idTeamLocal);

        return modelAssembler.toModel(m);
    }
    @PutMapping(path = "/match/{id}/teamOpponent/{idTeamOpponent}")
    public MatchModel addOpponentPlayers(@RequestBody @Valid List<PlayerInsertionModel> players, @PathVariable String id, @PathVariable Long idTeamOpponent) {

        Match m = matchService.addOpponentPlayers(players, id, idTeamOpponent);

        return modelAssembler.toModel(m);
    }
}
