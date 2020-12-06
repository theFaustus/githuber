package com.evil.inc.githuber;

import com.evil.inc.githuber.service.GitHubService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GithuberApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithuberApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner commandLineRunner(GitHubService gitHubService) {
		return args -> {
			System.out.println(gitHubService.getRepoCommitsByOrganizationAndByRepoName("theFaustus", "githuber"));
		};
	}
}
