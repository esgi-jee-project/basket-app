package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchRepository;
import com.esgi.jee.basket.exception.MatchNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;

    @GetMapping(path = "/matchs")
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @PostMapping(path = "/match")
    public Match create(@RequestBody Match match){
        System.out.println(match.getDate());
        System.out.println(match.getPlace());
        System.out.println(match.getId());
        return matchRepository.save(match);
    }

    @GetMapping(path = "/match/{id}")
    public Match getOne(@PathVariable Long id){

        return matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException(id));
    }
}
