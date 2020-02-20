package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import org.springframework.stereotype.Component;

@Component
public class NameValidator implements Validator<String> {
    private static final String NAME_REGEX;

    static {
        NAME_REGEX = "[A-z]{1}\\w{0,29}";
    }

    @Override
    public void validate(String name) throws IncorrectDataException {
        if(name==null){
            throw new IncorrectDataException("Value can't be null.");
        }
        if (!name.matches(NAME_REGEX)) {
            throw new IncorrectDataException("Value has invalid value \"" + name + "\".");
        }
    }
}

