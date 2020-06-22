package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.db.PlayerRepository;
import com.esgi.jee.basket.web.model.PlayerModel;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    final String firstname = "Michael";
    final String lastname = "Jordan";
    final Long id = 1L;

    @Before
    public void setUp(){
        this.playerService = new PlayerService(playerRepository);
    }

    public Player getTestPlayer(){

        return Player.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .build();
    }

    public PlayerModel getTestPlayerModel(){

        return new PlayerModel(firstname, lastname);
    }

    @Test
    public void should_return_list_on_find_all(){

        Pageable pageable = PageRequest.of(0, 20);
        List<Player> allPlayers = Collections.singletonList(getTestPlayer());
        Page<Player> allPlayersPage = new PageImpl<>(allPlayers, pageable, 40);

        when(playerRepository.findAll(any(Pageable.class))).thenReturn(allPlayersPage);

        Page<Player> result = playerService.findAll(pageable);
        assertThat(result.getTotalElements()).isEqualTo(40);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getFirstname()).isEqualTo(firstname);
        assertThat(result.getContent().get(0).getLastname()).isEqualTo(lastname);
    }

    @Test
    public void should_return_team_on_create() {

        final PlayerModel playerModel = getTestPlayerModel();
        final Player playerRepo = getTestPlayer();

        when(playerRepository.save(any(Player.class))).thenReturn(playerRepo);

        Player createdTeam = playerService.create(playerModel);
        assertThat(createdTeam.getId()).isEqualTo(id);
        assertThat(createdTeam.getFirstname()).isEqualTo(firstname);
        assertThat(createdTeam.getLastname()).isEqualTo(lastname);
    }

    @Test
    public void should_call_save_repository_method_on_create() {

        final PlayerModel playerModel = getTestPlayerModel();

        playerService.create(playerModel);

        ArgumentCaptor<Player> teamArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(teamArgumentCaptor.capture());
        Player exceptedPlayer = teamArgumentCaptor.getValue();
        assertThat(exceptedPlayer.getId()).isNull();
        assertThat(exceptedPlayer.getFirstname()).isEqualTo(firstname);
        assertThat(exceptedPlayer.getLastname()).isEqualTo(lastname);
    }

    @Test
    public void should_return_team_on_find_by_id(){

        final Player playerRepo = getTestPlayer();

        when(playerRepository.findById(id)).thenReturn(Optional.of(playerRepo));

        Optional<Player> createdPlayer = playerService.findById(id);
        assertThat(createdPlayer).hasValue(playerRepo);
        assertThat(createdPlayer).hasValueSatisfying(findTeam -> {
            assertThat(findTeam.getId()).isEqualTo(id);
            assertThat(findTeam.getFirstname()).isEqualTo(firstname);
            assertThat(findTeam.getLastname()).isEqualTo(lastname);
        });
    }

    @Test
    public void should_return_empty_optional_on_find_by_id(){

        when(playerRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Player> createdTeam = playerService.findById(id);
        assertThat(createdTeam).isEmpty();
    }

    @Test
    public void should_return_update_team_on_update(){

        final PlayerModel playerModel = new PlayerModel("Michael 3", null);
        final Player playerToUpdate = getTestPlayer();

        when(playerRepository.findById(id)).thenReturn(Optional.of(playerToUpdate));
        when(playerRepository.save(any(Player.class))).thenReturn(playerToUpdate);

        Optional<Player> createdTeam = playerService.update(id, playerModel);
        assertThat(createdTeam).hasValue(playerToUpdate);
        assertThat(createdTeam).hasValueSatisfying(findTeam -> {
            assertThat(findTeam.getId()).isEqualTo(id);
            assertThat(findTeam.getFirstname()).isEqualTo("Michael 3");
            assertThat(findTeam.getLastname()).isEqualTo(lastname);
        });
    }
}
