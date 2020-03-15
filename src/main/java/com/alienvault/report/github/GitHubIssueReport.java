package com.alienvault.report.github;

import com.alienvault.model.SimpleIssue;
import com.alienvault.model.SimpleRepo;
import com.alienvault.model.TopDay;
import com.alienvault.report.Reportable;
import com.alienvault.service.RepositoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is an implementation of the Reportable interface. Allows the report generator to generate reports given a s
 * specific implementation. This one builds the report as specified in the requirements.
 *
 * This class uses a RepositoryService interface to get the data needed for the report. Using an interface here we can
 * separate this class from the data layer and perform some dependency injection.
 */
public class GitHubIssueReport implements Reportable {
    //The service that will provide the data.
    private RepositoryService repositoryService;

    public GitHubIssueReport(RepositoryService service){
        this.repositoryService = service;
    }

    /**
     * Inherits this method from the interface. Basically creates a String representation of the report
     *
     * @param parameters
     * @return
     * @throws Exception
     */
    @Override
    public String generateReport(List<String> parameters) throws Exception {
        //Get the data from the service.
        List<SimpleRepo> repoList = this.repositoryService.getRepositoryInformation(parameters);

        //Build the issue list and get the top day.
        List<SimpleIssue> issueList = new ArrayList<SimpleIssue>();
        TopDay currentTopDay = new TopDay();

        for(SimpleRepo repo : repoList){
            issueList.addAll(repo.getIssues());
            TopDay topDay = repo.getTopDay();
            if (topDay.getCount() > currentTopDay.getCount()) {
                currentTopDay = topDay;
            }
        }

        //Sort the issues
        Collections.sort(issueList);

        //Build the report
        StringBuilder report = new StringBuilder();

        //Build the issue section
        report.append(buildIssueSection(issueList));
        report.append(",");

        //Build the Top Day summary
        report.append(buildTopDaySection(repoList, currentTopDay));
        report.append("\n}");

        return report.toString();
    }

    /**
     * Builds the TopDay Summary section given a list of SimpleRepos and the Current Top Day
     * @param repoList
     * @param currentTopDay
     * @return
     */
    private String buildTopDaySection(List<SimpleRepo> repoList, TopDay currentTopDay) {
        if(currentTopDay.getDate() == null){
            return "\"top_day\": { }";
        }else {

            StringBuilder topDay = new StringBuilder("\"top_day\": { \"day\": \"" + currentTopDay.getDate() + "\", \"occurences\": { ");

            for (int i = 0; i < repoList.size(); i++) {
                TopDay td = repoList.get(i).getTopDay(currentTopDay.getDate());
                topDay.append("\"" + td.getRepoName() + "\": " + td.getCount());

                if (i != repoList.size() - 1) {
                    topDay.append(",");
                }
            }

            topDay.append(" } } ");
            return topDay.toString();
        }
    }

    /**
     * Builds the Issue Section of the report given a list of issues. The issues will have been sorted prior to being
     * passed into this method. This method just builds the string.
     * @param issueList
     * @return
     */
    private String buildIssueSection(List<SimpleIssue> issueList) {
        StringBuilder builder = new StringBuilder("{\n \"issues\" : [ ");

        for(int i = 0; i < issueList.size(); i++){
            builder.append(issueList.get(i).toJson());

            if(i != issueList.size() -1){
                builder.append(",");
            }
        }

        builder.append(" ]\n");
        return builder.toString();
    }
}
