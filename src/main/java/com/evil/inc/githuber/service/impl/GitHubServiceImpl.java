package com.evil.inc.githuber.service.impl;

import com.evil.inc.githuber.domain.GitHubRepo;
import com.evil.inc.githuber.domain.GitHubUser;
import com.evil.inc.githuber.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
class GitHubServiceImpl implements GitHubService {
    public static final String GITHUB_API_URL = "https://api.github.com";
    private final RestTemplate restTemplate;


    @Override
    public GitHubUser getUserByUsername(String username) {
        return restTemplate.getForObject(GITHUB_API_URL + "/users/" + username, GitHubUser.class);
    }

    @Override
    public GitHubRepo getRepoByOrganizationAndByRepoName(String organizationName, String repoName) {
        return restTemplate.getForObject(GITHUB_API_URL + "/repos/" + organizationName + "/" + repoName, GitHubRepo.class);
    }

    @Override
    public List<GitHubUser> getContributorsByOrganizationAndByRepoName(String organizationName, String repoName) {
        return restTemplate.exchange(GITHUB_API_URL + "/repos/" + organizationName + "/" + repoName + "/contributors",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<GitHubUser>>() {
                }).getBody();
    }
}
