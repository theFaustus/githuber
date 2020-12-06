package com.evil.inc.githuber.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommit {

    private String sha;
    private Commit commit;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static
    class Commit {
        private String message;
        private Author author;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        static
        class Author {
            private String name;
            private String email;
            private LocalDateTime date;

        }
    }
}
