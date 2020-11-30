package com.evil.inc.githuber.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUser {
    @JsonProperty("login")
    private String username;
    private int id;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("html_url")
    private String url;
    private String type;
    private String name;
    private String company;
    private String location;
    private String email;
    private String bio;
    @JsonProperty("created_at")
    private LocalDateTime creationDate;
    @JsonProperty("public_repos")
    private int numberOfRepositories;
    @JsonProperty("followers")
    private int numberOfFollowers;
}
