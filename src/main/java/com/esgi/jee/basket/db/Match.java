package com.esgi.jee.basket.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column()
    private LocalDate date;

    @Column()
    private String place;


    @Column()
    private String nameLocal;

    @Column()
    private String nameOpponent;

    @Column()
    private int scoreLocal;

    @Column()
    private int scoreOpponent;

    @ManyToMany()
    private Set<Player> playerTeamLocal;

    @ManyToMany()
    private Set<Player> playerTeamOpponent;


}
