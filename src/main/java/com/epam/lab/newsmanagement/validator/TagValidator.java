package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagValidator implements Validator<Tag> {
    @Autowired
    private NameValidator validator;

    @Override
    public void validate(Tag tag) throws IncorrectDataException {
        if (tag == null) {
            throw new IncorrectDataException("Tag can't be null.");
        }
        String name = tag.getName();
        validator.validate(name);
    }
}
