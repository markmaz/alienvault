package com.alienvault.model;

import com.alienvault.model.SimpleIssue;
import com.alienvault.model.SimpleRepo;
import com.alienvault.model.TopDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRepoTest {
    private List<SimpleIssue> issues = new ArrayList<SimpleIssue>();
    private String topDayDate = "1971-01-27";

    @BeforeEach
    void setUp() {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH, 1);
        c1.set(Calendar.DAY_OF_MONTH, 27);
        c1.set(Calendar.YEAR, 1971);
        c1.set(Calendar.HOUR, 12);
        c1.set(Calendar.MINUTE, 42);
        c1.set(Calendar.SECOND, 11);
        Date date1 = c1.getTime();

        SimpleIssue issue = new SimpleIssue();
        issue.setId(1234L);
        issue.setRepository("Test/Test");
        issue.setCreateDate(date1);
        issue.setState("Closed");
        issue.setTitle("Test 1");
        issues.add(issue);

        c1.set(Calendar.YEAR, 2020);
        Date d2 = c1.getTime();

        issue = new SimpleIssue();
        issue.setId(1234L);
        issue.setRepository("Test/Test");
        issue.setCreateDate(d2);
        issue.setState("Open");
        issue.setTitle("Test 2");

        issues.add(issue);

        issue = new SimpleIssue();
        issue.setId(1234L);
        issue.setRepository("Test/Test");
        issue.setCreateDate(d2);
        issue.setState("Open");
        issue.setTitle("Test 3");

        issues.add(issue);
    }

    @Test
    void getTopDay() {
        SimpleRepo repo = new SimpleRepo();
        repo.setIssues(this.issues);

        TopDay topDay = repo.getTopDay();
        assertEquals(2, topDay.getCount());
        assertEquals(this.topDayDate, topDay.getDate());
    }

    @Test
    void testGetTopDay() {
        SimpleRepo repo = new SimpleRepo();
        repo.setIssues(this.issues);

        TopDay topDay = repo.getTopDay(this.topDayDate);
        assertEquals(2, topDay.getCount());
    }

    @Test
    void testGetTopDayWithNoDateInMap() {
        SimpleRepo repo = new SimpleRepo();
        repo.setIssues(this.issues);

        TopDay topDay = repo.getTopDay("1997-01-01");
        assertEquals(0, topDay.getCount());
    }

    @Test
    void testToString() {
        String expected = "{\"RepositoryName\":\"Test/Test\", \"IssueCount\":3}";
        SimpleRepo repo = new SimpleRepo("Test/Test", this.issues);
        assertEquals(expected, repo.toString());
    }
}