package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorValidator implements Validator<Author> {
    @Autowired
    private NameValidator validator;

    @Override
    public void validate(Author author) throws IncorrectDataException {
        if (author == null) {
            throw new IncorrectDataException("Author can't be null.");
        }
        String name = author.getName();
        validator.validate(name);
        String surname = author.getSurname();
        validator.validate(surname);
    }
}
