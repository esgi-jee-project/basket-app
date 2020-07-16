package com.esgi.jee.basket.team.infrastructure.dao;

import com.esgi.jee.basket.team.domain.model.Team;

public class HibernateTeamAdapter {

    public static Team modelToTeam(HibernateTeam team){

        return new Team(team.getId(), team.getName(), team.getCountry(), team.getPlayer(), team.getPlace());
    }

    public static HibernateTeam teamToModel(Team team){

        return HibernateTeam.builder()
                    .id(team.getId())
                    .name(team.getName())
                    .country(team.getCountry())
                    .player(team.getPlayer())
                    .place(team.getPlace())
                .build();
    }
}
