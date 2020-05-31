package com.esgi.jee.basket.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


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

   /* @Column(nullable = false)
    private Long idLocal;

    @Column(nullable = false)
    private Long idOpponent;

    */

}
