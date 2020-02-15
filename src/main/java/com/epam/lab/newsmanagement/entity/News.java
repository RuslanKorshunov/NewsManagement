package com.epam.lab.newsmanagement.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class News implements Cloneable {
    private long id;
    private String title;
    private String shortText;
    private String fullText;
    private Author author;
    private List<Tag> tags;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    public News() {
        tags = new ArrayList<>();
        creationDate = LocalDate.now();
        modificationDate = creationDate;
    }

    public News(long id, String title, String shortText, String fullText, Author author, List<Tag> tags, LocalDate creationDate, LocalDate modificationDate) {
        this.id = id;
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.author = author;
        this.tags = tags;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public News clone() throws CloneNotSupportedException {
        News news = (News) super.clone();
        Author author = this.author.clone();
        news.setAuthor(author);
        List<Tag> tags = new ArrayList<>();
        for (Tag tag : this.tags) {
            tags.add(tag.clone());
        }
        news.setTags(tags);
        return news;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", author=" + author +
                ", tags=" + tags +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
