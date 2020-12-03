package com.evil.inc.githuber.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubContributor {
    @JsonProperty("login")
    private String username;
    private int id;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String url;
    private String type;

}
