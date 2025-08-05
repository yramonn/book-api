package com.f1rst.bookapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenLibraryGenderResponseDTO {

    @JsonProperty("works")
    private List<BookByGenderDTO> works;

    public List<BookByGenderDTO> getWorks() {
        return works;
    }

    public void setWorks(List<BookByGenderDTO> works) {
        this.works = works;
    }
}
