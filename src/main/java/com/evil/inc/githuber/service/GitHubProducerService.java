package com.evil.inc.githuber.service;

import com.evil.inc.githuber.domain.GitHubRepo;

public interface GitHubProducerService {
    void sendRepo(GitHubRepo gitHubRepo);
}
