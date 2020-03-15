package com.alienvault.model;

import java.util.Objects;

public class TopDay implements Comparable<TopDay>{
    private int count;
    private String date;
    private String repoName;

    public TopDay(){}

    public TopDay(String date, int count, String repositoryName){
        this.count = count;
        this.date = date;
        this.repoName = repositoryName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }


    @Override
    public String toString() {
        return "{\"" + this.repoName + "\":" + this.count + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopDay topDay = (TopDay) o;
        return count == topDay.count &&
                Objects.equals(date, topDay.date) &&
                Objects.equals(repoName, topDay.repoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, date, repoName);
    }

    @Override
    public int compareTo(TopDay topDay) {
        return topDay.getCount() - count;
    }
}
