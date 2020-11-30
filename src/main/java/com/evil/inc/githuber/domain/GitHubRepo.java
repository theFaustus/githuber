package com.evil.inc.githuber.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepo {
    private int id;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private GitHubUser owner;
    @JsonProperty("html_url")
    private String url;
    private String description;
    @JsonProperty("created_at")
    private LocalDateTime creationDate;
    @JsonProperty("stargazers_count")
    private int numberOfStars;
    @JsonProperty("watchers_count")
    private int numberOfWatchers;
    private String language;
}
