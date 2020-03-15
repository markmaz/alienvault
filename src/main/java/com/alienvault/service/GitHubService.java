package com.alienvault.service;

import com.alienvault.model.SimpleRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The GitHub Service object. This class implements the RepositoryService interface. It uses the GitHubIssueService
 * to get the data from GitHub.
 */
public class GitHubService implements RepositoryService{
    public List<SimpleRepo> getRepositoryInformation(List<String> repositoryNames) throws Exception{
        //Create a list of Callables
        List<Callable<SimpleRepo>> callables = new ArrayList<Callable<SimpleRepo>>();

        //Basically this will create a thread for each repository that is passed in.
        for(String param : repositoryNames){

            //Check to see if the repo names are good. If not throw an exception.
            if(param.isEmpty() || !param.contains("/")){
                throw new IllegalArgumentException("Parameters must be in the format username/repository name.");
            }

            String[] path = param.split("/");
            String user = path[0];
            String repository = path[1];

            //TODO Abstract this dependecy
            callables.add(new GitHubIssueGrabber(user, repository));
        }

        //Create a threadpool
        ExecutorService executorService = Executors.newCachedThreadPool();

        //Couple of ArrayLists to hold the results
        List<Future<SimpleRepo>> futures = executorService.invokeAll(callables);
        List<SimpleRepo> repositories = new ArrayList<SimpleRepo>();

        //Get the top day and build the list of issues.
        for (Future<SimpleRepo> f : futures) {
            repositories.add(f.get());
        }

        executorService.shutdown();
        return repositories;
    }
}
