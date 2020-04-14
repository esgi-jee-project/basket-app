package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MatchController {

    private MatchRepository matchRepository;

    @GetMapping(path = "/matchs")
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @PostMapping(path = "/match")
    public Match create(@RequestBody Match match){

        return matchRepository.save(match);
    }
}
