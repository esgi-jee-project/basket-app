package com.esgi.jee.basket.team.infrastructure.dao;

import com.esgi.jee.basket.db.Contract;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HibernateTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String country;

    @OneToMany(mappedBy = "player")
    private Set<Contract> player;

    @Column(nullable = false)
    private String place;
}
