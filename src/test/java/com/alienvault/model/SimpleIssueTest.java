package com.alienvault.model;

import com.alienvault.model.SimpleIssue;
import org.eclipse.egit.github.core.Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class SimpleIssueTest {
    private Issue issue;
    private static final String EXPECTED_JSON = "{\"id\":1234, \"state\":\"Open\", \"title\":\"Test\", \"repository\":\"test/test\", \"create_at\":\"1971-02-28T00:42:11Z\"}";

    @BeforeEach
    void setUp() {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH, 1);
        c1.set(Calendar.DAY_OF_MONTH, 27);
        c1.set(Calendar.YEAR, 1971);
        c1.set(Calendar.HOUR, 12);
        c1.set(Calendar.MINUTE, 42);
        c1.set(Calendar.SECOND, 11);
        Date date = c1.getTime();

        this.issue = new Issue();
        this.issue.setCreatedAt(date);
        this.issue.setId(1234L);
        this.issue.setTitle("Test");
        this.issue.setState("Open");
    }

    @Test
    void toJson() {
        SimpleIssue simpleIssue = new SimpleIssue(issue, "test/test");
        assertEquals(EXPECTED_JSON, simpleIssue.toJson());
    }

    @Test
    void testToString() {
        SimpleIssue simpleIssue = new SimpleIssue(issue, "test/test");
        assertEquals(EXPECTED_JSON, simpleIssue.toJson());
    }
}