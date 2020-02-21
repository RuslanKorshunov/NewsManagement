package com.epam.lab.newsmanagement.validator;

import org.springframework.stereotype.Component;

@Component
public class NumberValidator {
    private static final String NUMBER_REGEX;

    static {
        NUMBER_REGEX = "\\d+";
    }

    public boolean validate(String number) {
        return number != null && number.matches(NUMBER_REGEX);
    }
}
