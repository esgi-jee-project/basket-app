package com.esgi.jee.basket.team.domain.model;

import com.esgi.jee.basket.db.Contract;

import java.util.Set;

public class Team {

    private Long id;

    private String name;

    private String country;

    private Set<Contract> player;

    private String place;

    public Team(Long id, String name, String country, Set<Contract> player, String place) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.player = player;
        this.place = place;
    }

    public Team(String name, String country, String place) {
        this.name = name;
        this.country = country;
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Contract> getPlayer() {
        return player;
    }

    public void setPlayer(Set<Contract> player) {
        this.player = player;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
