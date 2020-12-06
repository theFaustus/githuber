package com.evil.inc.githuber.service.impl;

import com.evil.inc.githuber.domain.GitHubCommit;
import com.evil.inc.githuber.domain.GitHubContributor;
import com.evil.inc.githuber.domain.GitHubRepo;
import com.evil.inc.githuber.domain.GitHubUser;
import com.evil.inc.githuber.service.GitHubProducerService;
import com.evil.inc.githuber.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
class GitHubServiceImpl implements GitHubService {
    public static final String GITHUB_API_URL = "https://api.github.com";
    private final RestTemplate restTemplate;
    private final GitHubProducerService gitHubProducerService;

    @Value("${github.personal.access.token}")
    private String personalAccessToken;


    @Override
    public GitHubUser getUserByUsername(String username) {
        ResponseEntity<GitHubUser> response = restTemplate.exchange(
                GITHUB_API_URL + "/users/" + username,
                HttpMethod.GET, getAuthorizedHttpEntity(), GitHubUser.class);
        System.out.println(response.getHeaders());
        return response.getBody();
    }

    private HttpEntity<String> getAuthorizedHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + personalAccessToken);
        return new HttpEntity<>("", headers);
    }

    @Override
    public GitHubRepo getRepoByOrganizationAndByRepoName(String organizationName, String repoName) {
        ResponseEntity<GitHubRepo> response = restTemplate.exchange(
                GITHUB_API_URL + "/repos/" + organizationName + "/" + repoName,
                HttpMethod.GET, getAuthorizedHttpEntity(), GitHubRepo.class);

        return response.getBody();
    }

    @Override
    public List<GitHubCommit> getRepoCommitsByOrganizationAndByRepoName(String organizationName, String repoName) {
        ResponseEntity<List<GitHubCommit>> response = restTemplate.exchange(
                GITHUB_API_URL + "/repos/" + organizationName + "/" + repoName + "/commits",
                HttpMethod.GET, getAuthorizedHttpEntity(), new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }


    @Override
    public List<GitHubUser> getContributorsByOrganizationAndByRepoName(String organizationName, String repoName) {
        ResponseEntity<List<GitHubContributor>> response = restTemplate.exchange(
                GITHUB_API_URL + "/repos/" + organizationName + "/" + repoName + "/contributors?perPage=50",
                HttpMethod.GET, getAuthorizedHttpEntity(), new ParameterizedTypeReference<>() {
                });
        System.out.println(response.getHeaders());
        List<GitHubContributor> contributors = response.getBody() != null ? response.getBody() : new ArrayList<>();
        List<GitHubUser> users = new ArrayList<>();
        contributors.forEach(c -> users.add(restTemplate.exchange(c.getUrl(), HttpMethod.GET, getAuthorizedHttpEntity(), GitHubUser.class).getBody()));
        populateCoordinatesAndColors(users);
        return users;
    }

    private void populateCoordinatesAndColors(List<GitHubUser> users) {
        users.forEach(u -> u.setColor(getRandomHexColor()));
        users.forEach(u -> u.setCity(CityCoordinatesManager.INSTANCE.getCoordinates(u.getLocation())));
    }

    private String getRandomHexColor() {
        Random randomGenerator = new Random();
        return String.format("#%02x%02x%02x", randomGenerator.nextInt(256), randomGenerator.nextInt(256), randomGenerator.nextInt(256)).toUpperCase();
    }
}
