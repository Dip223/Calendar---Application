
package com.mycompany.calendar_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;




class ReminderService {
    private ScheduledExecutorService scheduler;

    public ReminderService() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void scheduleReminder(Event event) {
    long delay = calculateDelay(event.getDateTime());
    if (delay > 0) {
        scheduler.schedule(() -> {
            JOptionPane.showMessageDialog(null, "Reminder: " + event);
        }, delay, TimeUnit.SECONDS);
    } else {
        System.out.println("Event " + event + " is in the past. No reminder scheduled.");
    }
}


    private long calculateDelay(LocalDateTime eventTime) {
        return java.time.Duration.between(LocalDateTime.now(), eventTime).getSeconds();
    }

    public void stopService() {
        scheduler.shutdown();
    }
}


