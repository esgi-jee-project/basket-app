package com.esgi.jee.basket.web.model;

import com.esgi.jee.basket.services.TeamService;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamModelTest {

    private Validator validator;

    @Before
    public void setUp(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void model_with_empty_name_should_be_not_valid(){

        final TeamModel teamModel = new TeamModel(null, "Chicago", "Chicago Place");

        Set<ConstraintViolation<TeamModel>> violations = validator.validate(teamModel);
        assertThat(violations.size()).isNotZero();
    }

    @Test
    public void model_with_empty_country_should_be_not_valid(){

        final TeamModel teamModel = new TeamModel("Chicago Bulls", null, "Chicago Place");

        Set<ConstraintViolation<TeamModel>> violations = validator.validate(teamModel);
        assertThat(violations.size()).isNotZero();
    }

    @Test
    public void model_with_empty_place_should_be_not_valid(){

        final TeamModel teamModel = new TeamModel("Chicago Bulls", "Chicago", null);

        Set<ConstraintViolation<TeamModel>> violations = validator.validate(teamModel);
        assertThat(violations.size()).isNotZero();
    }
}
