package com.esgi.jee.basket.team.infrastructure.controller;

import com.esgi.jee.basket.team.domain.model.Team;
import com.esgi.jee.basket.team.use_cases.CreateTeam;
import com.esgi.jee.basket.team.use_cases.FindTeam;
import com.esgi.jee.basket.team.use_cases.FindTeamById;
import com.esgi.jee.basket.team.use_cases.UpdateTeam;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeamControllerTest {

    private TeamController teamController;

    @Mock
    private FindTeam findTeam;

    @Mock
    private CreateTeam createTeam;

    @Mock
    private UpdateTeam updateTeam;

    @Mock
    private FindTeamById findTeamById;

    final String name = "Chicago Bulls";
    final String place = "Chicago place";
    final String country = "Chicago";
    final Long id = 1L;

    @Before
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        this.teamController = new TeamController(findTeam, createTeam, findTeamById, updateTeam, new PagedResourcesAssembler<>(null, null), new TeamModelAssembler());
    }

    public Team getTestTeam(){

        return new Team(id, name, country, new HashSet<>(), place);

    }

    public TeamModel getTestTeamModel(){

        return new TeamModel(name, country, place);
    }

    @Test
    public void should_return_team(){

        Pageable pageable = PageRequest.of(0, 20);
        List<Team> allTeams = Collections.singletonList(getTestTeam());
        Page<Team> allTeamsPage = new PageImpl<>(allTeams, pageable, 40);

        when(findTeam.execute(any(Pageable.class))).thenReturn(allTeamsPage);

        PagedModel<TeamModel> result = teamController.getAll(pageable);
        assertThat(result.getMetadata().getTotalElements()).isEqualTo(40);
        assertThat(result.getMetadata().getTotalPages()).isEqualTo(2);
        assertThat(result.getMetadata().getNumber()).isEqualTo(0);
        Assertions.assertThat(result.getContent()).hasSize(1);
        Assertions.assertThat(result.getContent()).anySatisfy(model -> {
            assertThat(model.getName()).isEqualTo(name);
            assertThat(model.getCountry()).isEqualTo(country);
            assertThat(model.getPlace()).isEqualTo(place);
        });
    }

    @Test
    public void should_return_team_on_create() {

        final TeamModel teamModel = getTestTeamModel();
        final Team teamRepo = getTestTeam();

        when(createTeam.execute(any(Team.class))).thenReturn(teamRepo);

        ResponseEntity<TeamModel> createdTeam = teamController.create(teamModel);
        assertThat(createdTeam.getBody()).satisfies(model -> {
            assertThat(model.getName()).isEqualTo(name);
            assertThat(model.getCountry()).isEqualTo(country);
        });
        assertThat(createdTeam.getStatusCodeValue()).isEqualTo(201);
        assertThat(createdTeam.getHeaders().containsKey("Location")).isTrue();
    }
}
