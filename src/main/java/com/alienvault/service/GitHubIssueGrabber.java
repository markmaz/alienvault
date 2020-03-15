package com.alienvault.service;

import com.alienvault.model.SimpleIssue;
import com.alienvault.model.SimpleRepo;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.IssueService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class GitHubIssueGrabber implements Callable<SimpleRepo> {
    private String repository;
    private String user;

    public GitHubIssueGrabber(String user, String repository){
        this.user = user;
        this.repository = repository;
    }

    public SimpleRepo call() throws Exception {
        IssueService service = new IssueService();
        Map<String, String> filter = new HashMap<String, String>();
        filter.put(IssueService.FIELD_DIRECTION, IssueService.DIRECTION_ASCENDING);
        filter.put(IssueService.FILTER_STATE, "all");


        PageIterator<Issue> issues = service.pageIssues(this.user, this.repository, filter);
        List<SimpleIssue> simpleIssues = new ArrayList<SimpleIssue>();
        String repoName = this.user + "/" + this.repository;

        while(issues.hasNext()){
            List<Issue> list  =  (List<Issue>)issues.next();

            for(Issue i : list) {
                simpleIssues.add(new SimpleIssue(i, repoName));
            }
        }

        return new SimpleRepo(repoName, simpleIssues);
    }
}
