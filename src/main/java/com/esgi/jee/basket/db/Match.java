package com.esgi.jee.basket.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Match {

    @Id
    private Long id;

    @Column(nullable = false)
    private Date date;
}
