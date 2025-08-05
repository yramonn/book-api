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


    @JsonProperty("key")
    public void setKey(String key) {
            if (key != null && key.startsWith("/works/")) {
                this.id = key.substring("/works/".length());
            } else {
                this.id = key;
            }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
}
