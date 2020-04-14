package com.esgi.jee.basket.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String firstname;

    @NonNull
    @Column(nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    private Set<Contract> contracts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Contract currentContract;
}
