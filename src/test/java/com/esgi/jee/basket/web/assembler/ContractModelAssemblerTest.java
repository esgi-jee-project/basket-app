package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Contract;
import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.team.infrastructure.controller.TeamModelAssembler;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.web.model.ContractModel;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractModelAssemblerTest {

    private ContractModelAssembler contractModelAssembler;

    @Before
    public void setUp(){
        TeamModelAssembler teamModelAssembler = new TeamModelAssembler();
        PlayerModelAssembler playerModelAssembler = new PlayerModelAssembler();
        this.contractModelAssembler = new ContractModelAssembler(teamModelAssembler, playerModelAssembler);
    }

    @Test
    public void should_return_player_model_on_convert() {

        long id = 1L;
        LocalDate startDate = LocalDate.of(2019, 8, 30);
        LocalDate endDate = LocalDate.now();
        String teamName = "Chicago";
        String teamCountry = "Chicago bulls";
        String playerFirstname = "Michael";
        String playerLastname = "Michael";
        Player contractPlayer = Player.builder()
                    .id(id)
                    .firstname(playerFirstname)
                    .lastname(playerLastname)
                .build();
        HibernateTeam contractTeam = HibernateTeam.builder()
                                .id(id)
                                .country(teamCountry)
                                .name(teamName)
                            .build();
        Contract contract = Contract.builder()
                                .startDate(startDate)
                                .endDate(endDate)
                                .player(contractPlayer)
                                .team(contractTeam)
                            .build();

        ContractModel convertContract = this.contractModelAssembler.toModel(contract);

        assertThat(convertContract.getStartDate()).isEqualTo(startDate);
        assertThat(convertContract.getEndDate()).isEqualTo(endDate);
        assertThat(convertContract.getTeam()).satisfies(team ->{
            assertThat(team.getCountry()).isEqualTo(teamCountry);
            assertThat(team.getName()).isEqualTo(teamName);
            assertThat(team.getLink("self")).isNotEmpty();
            assertThat(team.getLink("teams")).isNotEmpty();
            assertThat(team.getLink("contract")).isNotEmpty();
        });
        assertThat(convertContract.getPlayer()).satisfies(player ->{
            assertThat(player.getFirstname()).isEqualTo(playerFirstname);
            assertThat(player.getLastname()).isEqualTo(playerLastname);
            assertThat(player.getLink("self")).isNotEmpty();
            assertThat(player.getLink("players")).isNotEmpty();
            assertThat(player.getLink("contract")).isNotEmpty();
        });
        assertThat(convertContract.getLink("self")).isNotEmpty();
        assertThat(convertContract.getLink("team")).isNotEmpty();
        assertThat(convertContract.getLink("player")).isNotEmpty();
    }
}
