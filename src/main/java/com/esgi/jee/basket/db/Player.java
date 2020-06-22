package com.esgi.jee.basket.db;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "player")
    private Set<Contract> contracts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Contract currentContract;
}
