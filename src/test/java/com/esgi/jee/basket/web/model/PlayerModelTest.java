package com.esgi.jee.basket.web.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerModelTest {

    private Validator validator;

    @Before
    public void setUp(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void model_with_empty_firstname_should_be_not_valid(){

        final PlayerModel playerModel = new PlayerModel(null, "Jordan");

        Set<ConstraintViolation<PlayerModel>> violations = validator.validate(playerModel);
        assertThat(violations.size()).isNotZero();
    }

    @Test
    public void model_with_empty_lastname_should_be_not_valid(){

        final PlayerModel playerModel = new PlayerModel("Michael", null);

        Set<ConstraintViolation<PlayerModel>> violations = validator.validate(playerModel);
        assertThat(violations.size()).isNotZero();
    }
}
