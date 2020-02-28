package com.epam.lab.newsmanagement.dao.entity;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {
    private Author author;
    private List<Tag> tags;

    public SearchCriteria() {
        tags = new ArrayList<>();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
