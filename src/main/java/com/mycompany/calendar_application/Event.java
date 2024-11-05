
package com.mycompany.calendar_application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private final String name;
    private final Date date;
    private final String description;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Event(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Event: " + name + "\nDate and Time: " + dateFormat.format(date) + "\nDescription: " + description;
    }
}