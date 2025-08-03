package com.f1rst.bookapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookDTO {

    @JsonProperty("key")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author_name")
    private List<String> authorNames;

    @JsonProperty("author_key")
    private List<String> authorKeys;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public List<String> getAuthorKeys() {
        return authorKeys;
    }

    public void setAuthorKeys(List<String> authorKeys) {
        this.authorKeys = authorKeys;
    }
}
