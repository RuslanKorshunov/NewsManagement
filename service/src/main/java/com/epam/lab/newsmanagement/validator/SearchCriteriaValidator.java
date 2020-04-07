package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchCriteriaValidator implements Validator<SearchCriteria> {
    @Autowired
    private NameValidator nameValidator;
    @Autowired
    private TagValidator tagValidator;

    @Override
    public void validate(SearchCriteria sc) throws IncorrectDataException {
        Author author = sc.getAuthor();
        if (author != null) {
            String name = author.getName();
            nameValidator.validate(name);
            String surname = author.getSurname();
            nameValidator.validate(surname);
        }
    }
}
