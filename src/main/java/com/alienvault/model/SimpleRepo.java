package com.alienvault.model;

import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleRepo {
    private String repoName;
    private List<SimpleIssue> issues = new ArrayList<SimpleIssue>();
    private Map<String, Integer> map;

    public SimpleRepo(){}

    public SimpleRepo(String name, List<SimpleIssue> issues){
        this.repoName = name;
        this.issues = issues;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public List<SimpleIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<SimpleIssue> issues) {
        this.issues = issues;
    }

    public TopDay getTopDay(){
        initTopOfDayMap();

        if(map != null && map.entrySet().size() > 0) {
            String date = this.map.entrySet().stream().findFirst().get().getKey();
            int count = this.map.entrySet().stream().findFirst().get().getValue();

            return new TopDay(date, count, this.repoName);
        }else{
            return new TopDay(null, 0, this.repoName);
        }

    }

    public TopDay getTopDay(String date){
        initTopOfDayMap();
        int count = 0;

        if(map != null){
            if(map.get(date) == null){
                count = 0;
            }else {
                count = map.get(date);
            }
        }

        return new TopDay(date, count, this.repoName);
    }

    /**
     * Tricky little thing here - the date format in the requirements in UTC, however the number of occurences is just based on the day not time.
     * Need to store a string and not the actual date object.
     */
    private void initTopOfDayMap(){
        if(this.map == null) {
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
            Map<String, Integer> map = new HashMap<String, Integer>();

            for (SimpleIssue issue : this.issues) {
                String date = formatter.format(issue.getCreateDate());
                if (map.containsKey(date)) {
                    int currentCount = map.get(date);
                    currentCount++;
                    map.put(date, currentCount);
                } else {
                    map.put(date, 1);
                }
            }

            final Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
            map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
            this.map = sortedMap;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleRepo that = (SimpleRepo) o;
        return Objects.equals(repoName, that.repoName) &&
                Objects.equals(issues, that.issues) &&
                Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repoName, issues, map);
    }

    @Override
    public String toString() {
        return "{\"RepositoryName\":\"" + this.repoName + "\", \"IssueCount\":" + this.issues.size() + "}";
    }
}
