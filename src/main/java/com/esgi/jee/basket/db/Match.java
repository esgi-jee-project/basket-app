package com.esgi.jee.basket.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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


    @Column(nullable = false)
    private String nameLocal;

    @Column(nullable = false)
    private String nameOpponent;

    @Column(nullable = false)
    private Integer scoreLocal;

    @Column(nullable = false)
    private Integer scoreOpponent;

    @ManyToMany()
    @JsonSerialize
    private List<Player> playerTeamLocal;

    @ManyToMany()
    @JsonSerialize
    private List<Player> playerTeamOpponent;


}
