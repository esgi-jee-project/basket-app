package com.esgi.jee.basket.web;

import com.esgi.jee.basket.db.Player;
import com.esgi.jee.basket.services.PlayerService;
import com.esgi.jee.basket.web.assembler.PlayerModelAssembler;
import com.esgi.jee.basket.web.model.PlayerModel;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerControllerTest {

    private PlayerController playerController;

    @Mock
    private PlayerService playerService;

    final String firstname = "Michael";
    final String lastname = "Jordan";
    final Long id = 1L;

    @Before
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        this.playerController = new PlayerController(playerService, new PagedResourcesAssembler<>(null, null), new PlayerModelAssembler());
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
    public void should_return_team(){

        Pageable pageable = PageRequest.of(0, 20);
        List<Player> allPlayer = Collections.singletonList(getTestPlayer());
        Page<Player> allPlayerPage = new PageImpl<>(allPlayer, pageable, 40);

        when(playerService.findAll(any(Pageable.class))).thenReturn(allPlayerPage);

        PagedModel<PlayerModel> result = playerController.getAll(pageable);
        assertThat(result.getMetadata().getTotalElements()).isEqualTo(40);
        assertThat(result.getMetadata().getTotalPages()).isEqualTo(2);
        assertThat(result.getMetadata().getNumber()).isEqualTo(0);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).anySatisfy(model -> {
            assertThat(model.getFirstname()).isEqualTo(firstname);
            assertThat(model.getLastname()).isEqualTo(lastname);
        });
    }

    @Test
    public void should_return_team_on_create() {

        final PlayerModel playerModel = getTestPlayerModel();
        final Player playerRepo = getTestPlayer();

        when(playerService.create(any(PlayerModel.class))).thenReturn(playerRepo);

        ResponseEntity<PlayerModel> createdTeam = playerController.create(playerModel);
        assertThat(createdTeam.getBody()).satisfies(model -> {
            assertThat(model.getFirstname()).isEqualTo(firstname);
            assertThat(model.getLastname()).isEqualTo(lastname);
        });
        assertThat(createdTeam.getStatusCodeValue()).isEqualTo(201);
        assertThat(createdTeam.getHeaders().containsKey("Location")).isTrue();
    }
}
