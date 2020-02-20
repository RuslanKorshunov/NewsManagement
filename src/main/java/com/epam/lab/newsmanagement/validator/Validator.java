package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.exception.IncorrectDataException;

public interface Validator<T> {
    void validate(T t) throws IncorrectDataException;
}
