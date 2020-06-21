package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.web.assembler.TeamModelAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

//@RunWith(MockitoJUnitRunner.class)
//public class TeamControllerTest {
//
//    private TeamController teamController;
//
//    @Mock
//    private TeamRepository teamRepository;
//
//    @Mock
//    private TeamModelAssembler teamModelAssembler;
//
//    @Before
//    public void setUp() {
//        this.teamController = new TeamController(teamRepository, null, teamModelAssembler);
//    }
//
//    @Test
//    public void should_create_new_team(){
//
//        Team newTeam = Team.builder()
//                            .name("Lyon")
//                            .country("France")
//                            .build();
//
//        teamController.create(newTeam);
//        verify(teamRepository).save(newTeam);
//    }
//}
