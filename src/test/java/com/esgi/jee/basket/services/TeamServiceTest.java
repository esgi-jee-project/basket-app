package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import com.esgi.jee.basket.web.model.TeamModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceTest {

    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    final String name = "Chicago Bulls";
    final String country = "Chicago";
    final Long id = 1L;

    @Before
    public void setUp(){
        this.teamService = new TeamService(teamRepository);
    }

    public Team getTestTeam(){

       return Team.builder()
               .id(id)
               .name(name)
               .country(country)
               .build();
    }

    public TeamModel getTestTeamModel(){

        return new TeamModel(name, country);
    }

    @Test
    public void should_return_list_on_find_all(){

        Pageable pageable = PageRequest.of(0, 20);
        List<Team> allTeams = Collections.singletonList(getTestTeam());
        Page<Team> allTeamsPage = new PageImpl<>(allTeams, pageable, 40);

        when(teamRepository.findAll(any(Pageable.class))).thenReturn(allTeamsPage);

        Page<Team> result = teamService.findAll(pageable);
        assertThat(result.getTotalElements()).isEqualTo(40);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(name);
        assertThat(result.getContent().get(0).getCountry()).isEqualTo(country);
    }

    @Test
    public void should_return_team_on_create() {

        final TeamModel teamModel = getTestTeamModel();
        final Team teamRepo = getTestTeam();

        when(teamRepository.save(any(Team.class))).thenReturn(teamRepo);

        Team createdTeam = teamService.create(teamModel);
        assertThat(createdTeam.getId()).isEqualTo(id);
        assertThat(createdTeam.getName()).isEqualTo(name);
        assertThat(createdTeam.getCountry()).isEqualTo(country);
    }

    @Test
    public void should_call_save_repository_method_on_create() {

        final TeamModel teamModel = getTestTeamModel();

        teamService.create(teamModel);

        ArgumentCaptor<Team> teamArgumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).save(teamArgumentCaptor.capture());
        Team exceptedTeam = teamArgumentCaptor.getValue();
        assertThat(exceptedTeam.getId()).isNull();
        assertThat(exceptedTeam.getName()).isEqualTo(name);
        assertThat(exceptedTeam.getCountry()).isEqualTo(country);
    }

    @Test
    public void should_return_team_on_find_by_id(){

        final TeamModel teamModel = getTestTeamModel();
        final Team teamRepo = getTestTeam();

        when(teamRepository.findById(id)).thenReturn(Optional.of(teamRepo));

        Optional<Team> createdTeam = teamService.findById(id);
        assertThat(createdTeam).hasValue(teamRepo);
        assertThat(createdTeam).hasValueSatisfying(findTeam -> {
            assertThat(findTeam.getId()).isEqualTo(id);
            assertThat(findTeam.getName()).isEqualTo(name);
            assertThat(findTeam.getCountry()).isEqualTo(country);
        });
    }

    @Test
    public void should_return_empty_optional_on_find_by_id(){

        final TeamModel teamModel = getTestTeamModel();
        final Team teamRepo = getTestTeam();

        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Team> createdTeam = teamService.findById(id);
        assertThat(createdTeam).isEmpty();
    }

    @Test
    public void should_return_update_team_on_update(){

        final TeamModel teamModel = new TeamModel("Chicago 3", null);
        final Team teamToUpdate = getTestTeam();

        when(teamRepository.findById(id)).thenReturn(Optional.of(teamToUpdate));
        when(teamRepository.save(any(Team.class))).thenReturn(teamToUpdate);

        Optional<Team> createdTeam = teamService.update(id, teamModel);
        assertThat(createdTeam).hasValue(teamToUpdate);
        assertThat(createdTeam).hasValueSatisfying(findTeam -> {
            assertThat(findTeam.getId()).isEqualTo(id);
            assertThat(findTeam.getName()).isEqualTo("Chicago 3");
            assertThat(findTeam.getCountry()).isEqualTo(country);
        });
    }
}
