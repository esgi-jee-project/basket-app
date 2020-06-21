package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.web.model.TeamModel;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamModelAssemblerTest {

    private TeamModelAssembler teamModelAssembler;

    @Before
    public void setUp(){
        this.teamModelAssembler = new TeamModelAssembler();
    }

    @Test
    public void should_return_team_model_on_convert() {

            long id = 1L;
            String name = "Chicago Bulls";
            String country = "Chicago";
            Team toConvert = Team.builder()
                                    .id(id)
                                    .name(name)
                                    .country(country)
                                .build();

            TeamModel convertTeam = this.teamModelAssembler.toModel(toConvert);

            assertThat(convertTeam.getName()).isEqualTo(name);
            assertThat(convertTeam.getCountry()).isEqualTo(country);
            assertThat(convertTeam.getLink("self")).isNotEmpty();
            assertThat(convertTeam.getLink("teams")).isNotEmpty();
            assertThat(convertTeam.getLink("contract")).isNotEmpty();
    }
}
