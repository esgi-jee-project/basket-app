package com.esgi.jee.basket.web.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchCreateModelTest {

    private Validator validator;

    @Before
    public void setUp(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void  model_with_empty_date_should_not_be_valid () {
        LocalDate date = null;
        String place = "Paris";
        Long idLocalTeam = 7L;
        Long idOpponentTeam = 6L;
        MatchCreateModel matchCreateModel = new MatchCreateModel();
        matchCreateModel.setDate(date);
        matchCreateModel.setPlace(place);
        matchCreateModel.setIdTeamLocal(idLocalTeam);
        matchCreateModel.setIdTeamOpponent(idOpponentTeam);
        Set<ConstraintViolation<MatchCreateModel>> violations = validator.validate(matchCreateModel);
        assertThat(violations.size()).isNotZero();
    }

    @Test
    public void model_with_empty_place_should_not_be_valid () {
        LocalDate date =  LocalDate.now();
        Long idLocalTeam = 7L;
        Long idOpponentTeam = 6L;
        MatchCreateModel matchCreateModel = new MatchCreateModel();
        matchCreateModel.setDate(date);
        matchCreateModel.setPlace(null);
        matchCreateModel.setIdTeamLocal(idLocalTeam);
        matchCreateModel.setIdTeamOpponent(idOpponentTeam);
        Set<ConstraintViolation<MatchCreateModel>> violations = validator.validate(matchCreateModel);
        assertThat(violations.size()).isNotZero();
    }

}
