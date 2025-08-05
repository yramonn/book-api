package com.f1rst.bookapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookByGenderDTO {

    @JsonProperty("key")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subject")
    private List<String> subject;

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

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }
}
