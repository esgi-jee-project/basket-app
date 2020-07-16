package com.esgi.jee.basket.team.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTest {

    private Team getTeam() {

        return new Team("paris", "france", "paris");
    }

    @Test
    public void should_return_name(){

        Team team = getTeam();

        assertThat(team.getName()).isEqualTo("paris");
    }

    @Test
    public void should_return_country(){

        Team team = getTeam();

        assertThat(team.getCountry()).isEqualTo("france");
    }

    @Test
    public void should_return_place(){

        Team team = getTeam();

        assertThat(team.getPlace()).isEqualTo("paris");
    }
}
