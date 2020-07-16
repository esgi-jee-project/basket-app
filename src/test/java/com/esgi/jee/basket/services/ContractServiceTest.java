package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.*;
import com.esgi.jee.basket.team.infrastructure.controller.TeamModel;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.web.model.ContractModel;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContractServiceTest {

    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    final LocalDate startDate = LocalDate.of(2019, 8, 30);
    final LocalDate endDate = LocalDate.now();
    final String teamName = "Chicago";
    final String teamCountry = "Chicago bulls";
    final String teamPlace = "Chicago place";
    final String playerFirstname = "Michael";
    final String playerLastname = "Michael";
    final Long id = 1L;

    @Before
    public void setUp(){
        this.contractService = new ContractService(contractRepository);
    }

    public Contract getTestContract(){

        return Contract.builder()
                    .id(id)
                    .startDate(startDate)
                    .endDate(endDate)
                    .player(Player.builder()
                                .id(id)
                                .firstname(playerFirstname)
                                .lastname(playerLastname)
                            .build())
                    .team(HibernateTeam.builder()
                                .id(id)
                                .country(teamCountry)
                                .place(teamPlace)
                                .name(teamName)
                            .build())
                .build();
    }

    public ContractModel getTestContractModel(){

        return ContractModel.builder()
                    .startDate(startDate)
                    .endDate(endDate)
                    .team(new TeamModel(teamName, teamCountry, teamPlace))
                    .player(new PlayerModel(playerFirstname, playerLastname))
                .build();
    }

    @Test
    public void should_return_list_on_find_team_contract(){

        Pageable pageable = PageRequest.of(0, 20);
        List<Contract> allContracts = Collections.singletonList(getTestContract());
        Page<Contract> allContractsPage = new PageImpl<>(allContracts, pageable, 40);

        when(contractRepository.findTeamContract(anyLong(), any(Pageable.class))).thenReturn(allContractsPage);

        Page<Contract> result = contractService.findTeamContract(id, pageable);
        assertThat(result.getTotalElements()).isEqualTo(40);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getStartDate()).isEqualTo(startDate);
        assertThat(result.getContent().get(0).getEndDate()).isEqualTo(endDate);
    }

    @Test
    public void should_return_list_on_find_player_contract(){

        Pageable pageable = PageRequest.of(0, 20);
        List<Contract> allContracts = Collections.singletonList(getTestContract());
        Page<Contract> allContractsPage = new PageImpl<>(allContracts, pageable, 40);

        when(contractRepository.findPlayerContract(anyLong(), any(Pageable.class))).thenReturn(allContractsPage);

        Page<Contract> result = contractService.findPlayerContract(id, pageable);
        assertThat(result.getTotalElements()).isEqualTo(40);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getStartDate()).isEqualTo(startDate);
        assertThat(result.getContent().get(0).getEndDate()).isEqualTo(endDate);
    }

    @Test
    public void should_return_contract_on_create() {

        final ContractModel contractModel = getTestContractModel();
        final Contract contractRepo = getTestContract();
        final Player player = Player.builder()
                                        .firstname(playerFirstname)
                                        .lastname(playerLastname)
                                    .build();
        final HibernateTeam team = HibernateTeam.builder()
                                .name(teamName)
                                .country(teamCountry)
                            .build();

        when(contractRepository.save(any(Contract.class))).thenReturn(contractRepo);

        Contract createdTeam = contractService.create(contractModel, player, team);
        assertThat(createdTeam.getId()).isEqualTo(id);
        assertThat(createdTeam.getStartDate()).isEqualTo(startDate);
        assertThat(createdTeam.getEndDate()).isEqualTo(endDate);
        assertThat(createdTeam.getPlayer()).satisfies(playerFind -> {
            assertThat(playerFind.getFirstname()).isEqualTo(playerFirstname);
            assertThat(playerFind.getLastname()).isEqualTo(playerLastname);
        });
        assertThat(createdTeam.getTeam()).satisfies(teamFind -> {
            assertThat(teamFind.getName()).isEqualTo(teamName);
            assertThat(teamFind.getCountry()).isEqualTo(teamCountry);
        });
    }

    @Test
    public void should_return_contract_on_find_by_id(){

        final Contract contractRepo = getTestContract();

        when(contractRepository.findById(id)).thenReturn(Optional.of(contractRepo));

        Optional<Contract> createdContract = contractService.findById(id);
        assertThat(createdContract).hasValue(contractRepo);
        assertThat(createdContract).hasValueSatisfying(findContract -> {
            assertThat(findContract.getId()).isEqualTo(id);
            assertThat(findContract.getStartDate()).isEqualTo(startDate);
            assertThat(findContract.getEndDate()).isEqualTo(endDate);
        });
    }

    @Test
    public void should_return_empty_optional_on_find_by_id(){

        when(contractRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Contract> createdContract = contractService.findById(id);
        assertThat(createdContract).isEmpty();
    }

    @Test
    public void should_return_update_contract_on_update(){

        final LocalDate newStartDate = LocalDate.now();
        final ContractModel contractModel = new ContractModel(newStartDate, null, null, null);
        final Contract contractToUpdate = getTestContract();

        when(contractRepository.findById(id)).thenReturn(Optional.of(contractToUpdate));
        when(contractRepository.save(any(Contract.class))).thenReturn(contractToUpdate);

        Optional<Contract> createdTeam = contractService.update(id, contractModel);
        assertThat(createdTeam).hasValue(contractToUpdate);
        assertThat(createdTeam).hasValueSatisfying(findTeam -> {
            assertThat(findTeam.getId()).isEqualTo(id);
            assertThat(findTeam.getStartDate()).isEqualTo(newStartDate);
            assertThat(findTeam.getEndDate()).isEqualTo(endDate);
        });
    }
}
