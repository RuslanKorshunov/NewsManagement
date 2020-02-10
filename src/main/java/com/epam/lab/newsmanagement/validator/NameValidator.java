package com.epam.lab.newsmanagement.validator;

public class NameValidator {
    private static final String NAME_REGEX = "[A-z]{1}\\w{0,29}";

    public static boolean validate(String name) {
        return name.matches(NAME_REGEX);
    }
}

