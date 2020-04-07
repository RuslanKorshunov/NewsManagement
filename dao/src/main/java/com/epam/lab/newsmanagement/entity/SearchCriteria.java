package com.epam.lab.newsmanagement.entity;

public class SearchCriteria extends AbstractEntity {
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
