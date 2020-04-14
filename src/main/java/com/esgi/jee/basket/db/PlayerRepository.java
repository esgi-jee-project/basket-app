package com.esgi.jee.basket.db;

import com.esgi.jee.basket.db.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findAll();
}
