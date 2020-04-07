package com.epam.lab.newsmanagement.dto;

public class SearchCriteriaDto extends AbstractDto {
    private AuthorDto authorDto;

    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }
}
