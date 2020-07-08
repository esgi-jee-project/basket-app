package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchSearch;
import com.esgi.jee.basket.exception.InvalidFieldException;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import com.esgi.jee.basket.exception.MatchSearchNotFoundException;
import com.esgi.jee.basket.services.MatchSearchService;
import com.esgi.jee.basket.services.MatchService;
import com.esgi.jee.basket.web.assembler.MatchModelAssembler;
import com.esgi.jee.basket.web.model.MatchCreateModel;
import com.esgi.jee.basket.web.model.MatchModel;
import com.esgi.jee.basket.web.model.MatchSetScoreModel;
import com.esgi.jee.basket.web.model.PlayerInsertionModel;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/matchs")
@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchSearchService matchSearchService;
    private final PagedResourcesAssembler<Match> pagedResourcesAssembler;
    private final MatchModelAssembler modelAssembler;
    private final MatchService matchService;

    @GetMapping
    public PagedModel<MatchModel> getAll(Pageable pageable) {
        Page<Match> matchPage = matchService.getAll(pageable);
        return pagedResourcesAssembler.toModel(matchPage, modelAssembler);
    }

    @PostMapping
    public ResponseEntity<?> newGame(@RequestBody @Valid MatchCreateModel match){
        try {
            Match m = matchService.createGame(match);

            return new ResponseEntity<>(modelAssembler.toModel(m), HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/{id}")
    public MatchModel getOne(@PathVariable String id){

        try {
            Match findMatch = matchService.getOne(id);

            return modelAssembler.toModel(findMatch);
        }catch (InvalidFieldException e) {
            throw new MatchNotFoundException(id);
        }
    }

    @PutMapping(path = "/{id}/score")
    public MatchModel setScore(@RequestBody @Valid MatchSetScoreModel match, @PathVariable String id){
        Match m = matchService.setScore(match,id);
        return modelAssembler.toModel(m);
    }

    @PutMapping(path = "/{id}/teamLocal/{idTeamLocal}")
    public ResponseEntity<?> addLocalPlayers(@RequestBody @Valid List<PlayerInsertionModel> players, @PathVariable String id, @PathVariable Long idTeamLocal) {

        try {
            Match m = matchService.addLocalPlayers(players, id, idTeamLocal);
            return new ResponseEntity<>(modelAssembler.toModel(m), HttpStatus.OK);

        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Bad request" , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}/teamOpponent/{idTeamOpponent}")
    public ResponseEntity<?> addOpponentPlayers(@RequestBody @Valid List<PlayerInsertionModel> players, @PathVariable String id, @PathVariable Long idTeamOpponent) {

        try {
            Match m = matchService.addOpponentPlayers(players, id, idTeamOpponent);
            return new ResponseEntity<>(modelAssembler.toModel(m), HttpStatus.OK);

        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Bad request" , HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/search")
    public List<MatchModel> searchMatch(@RequestParam String f, HttpServletRequest servletRequest, KeycloakAuthenticationToken token) {

        List<Match> findValue = matchSearchService.search(f);

        KeycloakSecurityContext context = (KeycloakSecurityContext) token.getCredentials();
        MatchSearch matchSearch = matchSearchService.createHistory(findValue, f, context.getToken().getSubject(), servletRequest.getRemoteAddr());

        return findValue.stream().map(modelAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping(path = "/search/history")
    public Page<MatchSearch> searchMatchHistory(KeycloakAuthenticationToken token, Pageable pageable) {

        KeycloakSecurityContext context = (KeycloakSecurityContext) token.getCredentials();

        return matchSearchService.searchHistory(context.getToken().getSubject(), pageable);
    }

    @GetMapping(path = "/search/history/{id}")
    public MatchSearch searchMatchHistoryItem(@PathVariable String id, KeycloakAuthenticationToken token) {

        KeycloakSecurityContext context = (KeycloakSecurityContext) token.getCredentials();

        return matchSearchService.getHistoryItem(context.getToken().getSubject(), id).orElseThrow(() -> new MatchSearchNotFoundException(id));
    }
}
