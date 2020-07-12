package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.PlayerRepository;
import com.esgi.jee.basket.web.model.PlayerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Page<Player> findAll(Pageable pageable){

        return playerRepository.findAll(pageable);
    }

    public boolean existsById(long id) {

        return playerRepository.existsById(id);
    }

    public Player create(PlayerModel data){

        Player team = Player.builder()
                    .firstname(data.getFirstname())
                    .lastname(data.getLastname())
                .build();

        return playerRepository.save(team);
    }

    public Optional<Player> findById(Long id){

        return playerRepository.findById(id);
    }

    public Optional<Player> update(Long id, PlayerModel data){

        Optional<Player> find = playerRepository.findById(id);

        return find.map(team -> {
            team.setFirstname(data.getFirstname() != null ? data.getFirstname() : team.getFirstname());
            team.setLastname(data.getLastname() != null ? data.getLastname() : team.getLastname());

            return playerRepository.save(team);
        });
    }

    public Optional<Player> updateCurrentContract(long id, Contract contract){

        return playerRepository.findById(id).map(player -> {

            player.setCurrentContract(contract);
            playerRepository.save(player);

            return player;
        });
    }
}
