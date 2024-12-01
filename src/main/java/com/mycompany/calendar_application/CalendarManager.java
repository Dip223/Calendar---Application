
package com.mycompany.calendar_application;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;




class CalendarManager {
    private List<Event> events = new ArrayList<>();
    private static final String FILE_PATH = "calendar_data.txt";

    public CalendarManager() {
        this.events = new ArrayList<>();
        loadEvents();
    }



    public void addEvent(Event event) {
    events.add(event);
    saveEvents(); // Save to file immediately after adding
}


    public List<Event> getEvents() {
        return events;
    }

    public void saveEvents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Event event : events) {
                writer.write(event.getDateTime().toString() + "," + event.getDescription());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving events: " + e.getMessage());
        }
    }

    public void loadEvents() {
    events.clear();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] parts = line.split(",", 2);
                LocalDateTime dateTime = LocalDateTime.parse(parts[0]);
                String description = parts[1];
                events.add(new Event(dateTime, description));
            } catch (Exception e) {
                System.err.println("Error parsing event line: " + line);
            }
        }
    } catch (IOException e) {
        System.out.println("No existing calendar data found.");
    }
}

}
