package com.alienvault.report.github;

import com.alienvault.model.SimpleIssue;
import com.alienvault.model.SimpleRepo;
import com.alienvault.service.RepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubIssueReportTest {
    List<SimpleRepo> repoList = new ArrayList<SimpleRepo>();
    String expectedReport = "{\n \"issues\" : [ {\"id\":1, \"state\":\"closed\", \"title\":\"test\", \"repository\":\"test/test\", \"create_at\":\"1971-02-28T00:42:11Z\"} ]\n,\"top_day\":{\"day\":\"1971-02-28\", \"occurences\": {\"test/test\": 1}}\n}";

    @BeforeEach
    void setUp(){
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH, 1);
        c1.set(Calendar.DAY_OF_MONTH, 27);
        c1.set(Calendar.YEAR, 1971);
        c1.set(Calendar.HOUR, 12);
        c1.set(Calendar.MINUTE, 42);
        c1.set(Calendar.SECOND, 11);
        Date date = c1.getTime();

        List<SimpleIssue> issues = new ArrayList<SimpleIssue>();
        SimpleIssue issue = new SimpleIssue();
        issue.setCreateDate(date);
        issue.setId(1);
        issue.setRepository("test/test");
        issue.setState("closed");
        issue.setTitle("test");
        issues.add(issue);

        SimpleRepo repo = new SimpleRepo("test/test", issues);
        this.repoList.add(repo);
    }

    @Test
    void generateReport() {
        RepositoryService service = mock(RepositoryService.class);
        GitHubIssueReport issueReport = new GitHubIssueReport(service);

        String[] repos = {"test/test", "test/test"};

        try {

            when(service.getRepositoryInformation(Arrays.asList(repos))).thenReturn(this.repoList);
            String report = issueReport.generateReport(Arrays.asList(repos));

            assertEquals(expectedReport, report);

        } catch (Exception e) {
            fail("No Exception should be thrown");
        }

    }
}