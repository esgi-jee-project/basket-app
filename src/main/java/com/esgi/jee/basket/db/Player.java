package com.esgi.jee.basket.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Player {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private String firstname;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    private Set<Contract> contracts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @BatchSize(size = 20)
    private Contract currentContract;
}
