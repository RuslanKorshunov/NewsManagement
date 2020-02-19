package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import org.springframework.stereotype.Component;

@Component
public class NumberValidator implements Validator<String> {
    private static final String NUMBER_REGEX;

    static {
        NUMBER_REGEX = "\\d+";
    }

    @Override
    public void validate(String number) throws IncorrectDataException {
        if (number == null) {
            throw new IncorrectDataException("Number can't be null.");
        }
        if (!number.matches(NUMBER_REGEX)) {
            throw new IncorrectDataException("Number has invalid value \"" + number + "\"");
        }
    }
}
