package com.epam.lab.newsmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news")
public class News extends AbstractEntity implements Cloneable {
    @Id
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "short_text")
    private String shortText;
    @Column(name = "full_text")
    private String fullText;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "news_author",
            joinColumns = {@JoinColumn(name = "news_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")})
    private Author author;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "news_tag",
            joinColumns = {@JoinColumn(name = "news_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    private List<Tag> tags;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "modification_date")
    private LocalDate modificationDate;

    public News() {
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
