package com.alienvault.model;

import com.alienvault.model.TopDay;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopDayTest {
    private String date1 = "2020-03-15";

    @Test
    void testToString() {
       String expected =  "{\"Test/Test\":2}";
        TopDay topDay1 = new TopDay(this.date1, 2, "Test/Test");
        assertEquals(expected, topDay1.toString());
    }

    @Test
    void compareTo() {
        TopDay topDay1 = new TopDay(this.date1, 2, "Test/Test");
        TopDay topDay2 = new TopDay(this.date1, 1, "Test/Test");

        int results = topDay1.compareTo(topDay2);
        assertEquals(-1, results);
    }
}