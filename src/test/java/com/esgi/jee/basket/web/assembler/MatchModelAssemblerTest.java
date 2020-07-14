package com.esgi.jee.basket.web.assembler;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.Team;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class MatchModelAssemblerTest {

    private MatchModelAssembler matchModelAssembler;

    @Before
    public void setUp (){
        TeamModelAssembler teamModelAssembler = new TeamModelAssembler();
        PlayerModelAssembler playerModelAssembler = new PlayerModelAssembler();
        this.matchModelAssembler = new MatchModelAssembler(teamModelAssembler, playerModelAssembler);
    }

    @Test
    public void should_return_match_model_on_convert () {
        Long id = 1L;
        LocalDate date = LocalDate.now();
        String place = "Los Angeles";
        String localTeamName = "Lakers";
        String opponentTeamName = "Celtics";
        String localTeamCountry = "USA";
        String opponentTeamCountry = "USA";
        String localTeamPlayerFirstname = "Kobe";
        String localTeamPlayerLastname = "BRYANT";
        String oppenentTeamPlayerFirstname = "Rajon";
        String opponentTeamPlayerLastname = "RONDO";
        int localScore = 90;
        int opponentScore = 91;

        Player localPlayer = Player.builder()
                                        .id(id)
                                        .firstname(localTeamPlayerFirstname)
                                        .lastname(localTeamPlayerLastname)
                                    .build();
        Player opponentPlayer = Player.builder()
                                            .id(id)
                                            .firstname(oppenentTeamPlayerFirstname)
                                            .lastname(opponentTeamPlayerLastname)
                                        .build();
        Team localTeam = Team.builder()
                                .name(localTeamName)
                                .country(localTeamCountry)
                            .build();
        Team opponentTeam = Team.builder()
                                    .name(opponentTeamName)
                                    .country(opponentTeamCountry)
                                .build();
        Math match = Match.builder()
                            .date("2020-10-10")
                            .playerTeamLocal(localPlayer.getId())
                            .playerTeamOpponent(opponentPlayer)
                            .idNameLocal(localTeam)
                            .idNameOpponent(opponentTeam)
                            .scoreLocal(localScore)
                            .scoreOpponent(opponentScore)
                        .build();



    }
}
