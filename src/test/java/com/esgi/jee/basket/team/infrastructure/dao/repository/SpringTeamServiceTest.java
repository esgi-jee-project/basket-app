package com.esgi.jee.basket.team.infrastructure.dao.repository;

import com.esgi.jee.basket.team.domain.model.Team;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Sql(
    statements = "INSERT INTO Team(id, name, country, place) VALUES (1, 'Paris', 'France', 'France'), (2, 'Barcelone', 'Spain', 'Spain'), (3, 'Manchester', 'England', 'England')",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        statements = "DELETE FROM Team",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class SpringTeamServiceTest {

    @Autowired
    SpringTeamRepository teamRepository;


    Pageable pageable = PageRequest.of(1, 20);

    @Test
    public void should_find_all_team(){

        assertThat(teamRepository.findAll(pageable)).hasSize(3);
    }

    @Test
    public void should_find_by_id(){

        HibernateTeam findTeam = teamRepository.findById(1L).orElseThrow(AssertionFailedError::new);

        assertThat(findTeam.getName()).isEqualTo("Paris");
        assertThat(findTeam.getCountry()).isEqualTo("France");
    }
}
