package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchRepository;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import com.esgi.jee.basket.services.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final MatchService matchService;

    @GetMapping(path = "/matchs")
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @PostMapping(path = "/match")
    public Match create(@RequestBody Match match){
        return matchRepository.save(match);
    }

    @PostMapping(path = "/game")
    public Match newGame(@RequestBody Match match){
        Match m = matchService.createGame(match);
        return matchRepository.save(m);
    }

    @PostMapping(path = "/matchss")
    public Match getTeams(@RequestBody Match match) {
        Match m = matchService.createMatch(match);
        System.out.println("ID : " + m.getId());
        System.out.println("Date : " + m.getDate());
        System.out.println("Lieu : " + m.getPlace());
        System.out.println("Equipe local : " + m.getNameLocal());
        System.out.println("Equipe adverse : " + m.getNameOpponent());
        return matchRepository.save(m);
    }

    @GetMapping(path = "/match/{id}")
    public Match getOne(@PathVariable Long id){

        return matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException(id));
    }
}
