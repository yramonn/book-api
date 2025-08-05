package com.f1rst.bookapi.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenLibraryByKeyResponseDTO {

    @JsonProperty("key")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("key")
    public void setKey(String key) {
        if (key != null && key.startsWith("/works/")) {
            this.id = key.substring("/works/".length());
        } else {
            this.id = key;
        }
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

    public void setTitle(String title) {
        this.title = title;
    }
}
