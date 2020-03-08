package com.epam.lab.newsmanagement.dto;

import java.util.List;

public class SearchCriteriaDto extends AbstractDto {
    private AuthorDto authorDto;
    private List<TagDto> tagDtoList;

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
}
