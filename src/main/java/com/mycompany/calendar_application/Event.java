
package com.mycompany.calendar_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single event.
 */
class Event {
    private LocalDateTime dateTime;
    private String description;

    public Event(LocalDateTime dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    
    @Override
    public String toString() {
        return dateTime + " - " + description;
    }
    
    
//    @Override
//    public String toString() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return dateTime.format(formatter) + " - " + description;
//    }
}


