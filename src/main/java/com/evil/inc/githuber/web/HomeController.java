package com.evil.inc.githuber.web;

import com.evil.inc.githuber.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final GitHubService gitHubService;

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("get-user")
    public ModelAndView getUser(@RequestParam("username") String username) {
        ModelAndView mav = home();
        mav.addObject("gitHubUser", gitHubService.getUserByUsername(username));
        return mav;
    }

    @GetMapping("get-repo")
    public ModelAndView getUser(@RequestParam("orgName") String orgName, @RequestParam("repoName") String repoName) {
        ModelAndView mav = home();
        mav.addObject("gitHubRepo", gitHubService.getRepoByOrganizationAndByRepoName(orgName, repoName));
        return mav;
    }

    @GetMapping("get-contribs")
    public ModelAndView getContributors(@RequestParam("orgName") String orgName, @RequestParam("repoName") String repoName) {
        ModelAndView mav = home();
        mav.addObject("gitHubRepoContributors", gitHubService.getContributorsByOrganizationAndByRepoName(orgName, repoName));
        return mav;
    }

    @GetMapping("get-commits")
    public ModelAndView getCommits(@RequestParam("orgName") String orgName, @RequestParam("repoName") String repoName) {
        ModelAndView mav = home();
        mav.addObject("gitHubRepoCommits", gitHubService.getRepoCommitsByOrganizationAndByRepoName(orgName, repoName));
        return mav;
    }

}
