package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.web.model.PlayerModel;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerModelAssemblerTest {

    private PlayerModelAssembler playerModelAssembler;

    @Before
    public void setUp(){
        this.playerModelAssembler = new PlayerModelAssembler();
    }

    @Test
    public void should_return_player_model_on_convert() {

        long id = 1L;
        String firstname = "Michael";
        String lastname = "Jordan";
        Player toConvert = Player.builder()
                    .id(id)
                    .firstname(firstname)
                    .lastname(lastname)
                .build();

        PlayerModel convertTeam = this.playerModelAssembler.toModel(toConvert);

        assertThat(convertTeam.getFirstname()).isEqualTo(firstname);
        assertThat(convertTeam.getLastname()).isEqualTo(lastname);
        assertThat(convertTeam.getLink("self")).isNotEmpty();
        assertThat(convertTeam.getLink("players")).isNotEmpty();
        assertThat(convertTeam.getLink("contract")).isNotEmpty();
    }
}
