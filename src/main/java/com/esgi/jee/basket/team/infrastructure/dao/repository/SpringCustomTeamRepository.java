package com.esgi.jee.basket.team.infrastructure.dao.repository;

import com.esgi.jee.basket.team.domain.TeamRepository;
import com.esgi.jee.basket.team.domain.model.Team;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeamAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class SpringCustomTeamRepository implements TeamRepository {

    private final SpringTeamRepository springTeamRepository;

    public SpringCustomTeamRepository(SpringTeamRepository springTeamRepository) {
        this.springTeamRepository = springTeamRepository;
    }

    @Override
    public Page<Team> findAll(Pageable pageable) {

        Page<HibernateTeam> allTeam = springTeamRepository.findAll(pageable);

        return allTeam.map((Function<? super HibernateTeam, Team>) team -> HibernateTeamAdapter.modelToTeam((HibernateTeam) team));
    }

    @Override
    public boolean existsById(Long id) {

        return springTeamRepository.existsById(id);
    }

    @Override
    public Team save(Team team) {

        return HibernateTeamAdapter.modelToTeam(
                springTeamRepository.save(HibernateTeamAdapter.teamToModel(team))
        );
    }

    @Override
    public Optional<Team> findById(Long id) {
        return springTeamRepository
                    .findById(id)
                    .map(HibernateTeamAdapter::modelToTeam);
    }

    @Override
    public Optional<Team> update(long id, Team data){

        Optional<HibernateTeam> find = springTeamRepository.findById(id);

        return find.map(team -> {
            team.setName(data.getName() != null ? data.getName() : team.getName());
            team.setCountry(data.getCountry() != null ? data.getCountry() : team.getCountry());

            return springTeamRepository.save(team);
        }).map(HibernateTeamAdapter::modelToTeam);
    }
}
