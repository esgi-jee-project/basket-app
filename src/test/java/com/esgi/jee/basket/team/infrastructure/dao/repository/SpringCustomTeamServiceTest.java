package com.esgi.jee.basket.team.infrastructure.dao.repository;

import com.esgi.jee.basket.services.TeamService;
import com.esgi.jee.basket.team.domain.model.Team;
import com.esgi.jee.basket.team.infrastructure.controller.TeamModel;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpringCustomTeamServiceTest {

    private SpringCustomTeamRepository springCustomTeamRepository;

    @Mock
    private SpringTeamRepository teamRepository;

    final String name = "Chicago Bulls";
    final String country = "Chicago";
    final String teamPlace = "Chicago place";

    final Long id = 1L;

    @Before
    public void setUp(){
        this.springCustomTeamRepository = new SpringCustomTeamRepository(teamRepository);
    }

    public Team getTestTeam(){

        return new Team(id, name, country, new HashSet<>(), teamPlace);
    }

    public HibernateTeam getHibernateTeam(){

        return HibernateTeam.builder()
                    .id(id)
                    .name(name)
                    .country(country)
                    .place(teamPlace)
                .build();
    }

    public TeamModel getTestTeamModel(){

        return new TeamModel(name, country, teamPlace);
    }

    @Test
    public void should_return_list_on_find_all(){

        Pageable pageable = PageRequest.of(0, 20);
        List<HibernateTeam> allTeams = Collections.singletonList(getHibernateTeam());
        Page<HibernateTeam> allTeamsPage = new PageImpl<>(allTeams, pageable, 40);

        when(teamRepository.findAll(any(Pageable.class))).thenReturn(allTeamsPage);

        Page<Team> result = springCustomTeamRepository.findAll(pageable);
        assertThat(result.getTotalElements()).isEqualTo(40);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(name);
        assertThat(result.getContent().get(0).getCountry()).isEqualTo(country);
    }

    @Test
    public void should_return_team_on_create() {

        final Team teamModel = getTestTeam();
        final HibernateTeam teamRepo = getHibernateTeam();

        when(teamRepository.save(any(HibernateTeam.class))).thenReturn(teamRepo);

        Team createdTeam = springCustomTeamRepository.save(teamModel);
        assertThat(createdTeam.getId()).isEqualTo(id);
        assertThat(createdTeam.getName()).isEqualTo(name);
        assertThat(createdTeam.getCountry()).isEqualTo(country);
    }

    @Test
    public void should_return_team_on_find_by_id(){

        final HibernateTeam teamRepo = getHibernateTeam();
        final Team team = getTestTeam();

        when(teamRepository.findById(id)).thenReturn(Optional.of(teamRepo));

        Optional<Team> createdTeam = springCustomTeamRepository.findById(id);
        assertThat(createdTeam).hasValueSatisfying(findTeam -> {
            assertThat(findTeam.getId()).isEqualTo(id);
            assertThat(findTeam.getName()).isEqualTo(name);
            assertThat(findTeam.getCountry()).isEqualTo(country);
        });
    }

    @Test
    public void should_return_empty_optional_on_find_by_id(){

        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Team> createdTeam = springCustomTeamRepository.findById(id);
        assertThat(createdTeam).isEmpty();
    }

    @Test
    public void should_return_update_team_on_update(){

        final Team teamModel = new Team("Chicago 3", null, null);
        final HibernateTeam teamToUpdate = getHibernateTeam();

        when(teamRepository.findById(id)).thenReturn(Optional.of(teamToUpdate));
        when(teamRepository.save(any(HibernateTeam.class))).thenReturn(teamToUpdate);

        Optional<Team> createdTeam = springCustomTeamRepository.update(id, teamModel);
        assertThat(createdTeam).hasValueSatisfying(findTeam -> {
            assertThat(findTeam.getId()).isEqualTo(id);
            assertThat(findTeam.getName()).isEqualTo("Chicago 3");
            assertThat(findTeam.getCountry()).isEqualTo(country);
        });
    }
}
