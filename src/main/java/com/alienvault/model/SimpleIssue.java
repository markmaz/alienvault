package com.alienvault.model;


import org.eclipse.egit.github.core.Issue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SimpleIssue implements Comparable<SimpleIssue>
{
    private String title;
    private String state;
    private String repository;
    private Date createDate;
    private long id;

    public SimpleIssue(){}

    public SimpleIssue(Issue issue, String repository){
        this.title = issue.getTitle();
        this.state = issue.getState();
        this.createDate = issue.getCreatedAt();
        this.id = issue.getId();
        this.repository = repository;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int compareTo(SimpleIssue simpleIssue) {
        if(getCreateDate() == null || simpleIssue.getCreateDate() == null) {
            return 0;
        }else{
            return getCreateDate().compareTo(simpleIssue.getCreateDate());
        }
    }

    private String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return formatter.format(this.createDate);
    }

    public String toJson(){
        return "{ \"id\": " + this.id + ", \"state\": \"" + this.state + "\", \"title\": \"" + this.title + "\", \"repository\": \"" +  this.repository + "\", \"created_at\": \"" +  getFormattedDate() + "\" }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleIssue issue = (SimpleIssue) o;
        return id == issue.id &&
                Objects.equals(title, issue.title) &&
                Objects.equals(state, issue.state) &&
                Objects.equals(repository, issue.repository) &&
                Objects.equals(createDate, issue.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, state, repository, createDate, id);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
