package com.esgi.jee.basket.repository;

import com.esgi.jee.basket.db.Team;
import com.esgi.jee.basket.db.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Sql(
    statements = "INSERT INTO Team(id, name, country) VALUES (1, 'Paris', 'France'), (2, 'Barcelone', 'Spain'), (3, 'Manchester', 'England')",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        statements = "DELETE FROM Team",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class TeamRepositoryTest {

    @Autowired  TeamRepository teamRepository;

    @Test
    public void should_find_all_team(){

        assertThat(teamRepository.findAll()).hasSize(3);
    }

    @Test
    public void should_find_by_id(){

        Team findTeam = teamRepository.findById(1L).orElseThrow(AssertionFailedError::new);

        assertThat(findTeam.getName()).isEqualTo("Paris");
        assertThat(findTeam.getCountry()).isEqualTo("France");
    }
}
