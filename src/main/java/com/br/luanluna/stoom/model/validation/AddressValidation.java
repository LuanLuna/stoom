package com.br.luanluna.stoom.model.validation;

import com.br.luanluna.stoom.model.Address;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AddressValidation {

    public static List<String> validate(Address address) {
        List<String> errors = new ArrayList<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        if (violations != null && violations.size() > 0) {
            violations.forEach(error -> {
                errors.add(error.getMessage());
            });
        }

        return errors;
    }
}
