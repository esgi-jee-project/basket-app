package com.esgi.jee.basket.web.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractModelTest {

    private Validator validator;

    @Before
    public void setUp(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void model_with_empty_start_date_should_be_not_valid(){

        final ContractModel contractModel = new ContractModel(null, null, null, null);

        Set<ConstraintViolation<ContractModel>> violations = validator.validate(contractModel);
        assertThat(violations.size()).isNotZero();
    }
}
