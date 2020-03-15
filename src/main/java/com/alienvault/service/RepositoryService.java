package com.alienvault.service;

import com.alienvault.model.SimpleRepo;

import java.util.List;

public interface RepositoryService {
    public List<SimpleRepo> getRepositoryInformation(List<String> repositoryNames) throws Exception;
}
