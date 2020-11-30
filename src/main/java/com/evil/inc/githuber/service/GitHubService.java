package com.evil.inc.githuber.service;

import com.evil.inc.githuber.domain.GitHubRepo;
import com.evil.inc.githuber.domain.GitHubUser;

import java.util.List;

public interface GitHubService {

    GitHubUser getUserByUsername(String username);

    GitHubRepo getRepoByOrganizationAndByRepoName(String organizationName, String repoName);

    List<GitHubUser> getContributorsByOrganizationAndByRepoName(String organizationName, String repoName);

}
