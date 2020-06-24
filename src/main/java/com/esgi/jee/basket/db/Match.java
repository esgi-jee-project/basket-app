package com.esgi.jee.basket.db;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column()
    private LocalDate date;

    @Column()
    private String place;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_local_id", nullable = false)
    private Team idNameLocal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_opponent_id", nullable = false)
    private Team idNameOpponent;

    @Column(nullable = false)
    private Integer scoreLocal;

    @Column(nullable = false)
    private Integer scoreOpponent;

    @ManyToMany()
    private List<Player> playerTeamLocal;

    @ManyToMany()
    private List<Player> playerTeamOpponent;


}
