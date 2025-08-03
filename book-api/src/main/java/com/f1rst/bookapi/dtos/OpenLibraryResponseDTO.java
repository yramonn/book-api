package com.f1rst.bookapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenLibraryResponseDTO {

    @JsonProperty("docs")
    private List<BookDTO> docs;

    public List<BookDTO> getDocs() {
        return docs;
    }

    public void setDocs(List<BookDTO> docs) {
        this.docs = docs;
    }
}
