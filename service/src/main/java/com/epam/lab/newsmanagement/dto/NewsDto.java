package com.epam.lab.newsmanagement.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsDto extends AbstractDto {
    private long id;
    private String title;
    private String shortText;
    private String fullText;
    private AuthorDto authorDto;
    private List<TagDto> tagDtoList;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    public NewsDto() {
        this.title = "";
        this.shortText = "";
        this.fullText = "";
        tagDtoList = new ArrayList<>();
        creationDate = LocalDate.now();
        modificationDate = creationDate;
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

    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }

    public List<TagDto> getTagDtoList() {
        return tagDtoList;
    }

    public void setTagDtoList(List<TagDto> tagDtoList) {
        this.tagDtoList = tagDtoList;
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
}
