
package com.mycompany.calendar_application;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CalendarApplication {
    
    
    private CalendarManager calendarManager; // Correctly defined and implemented below
    private ReminderService reminderService; // Correctly defined and implemented below
    private JPanel calendarPanel;

    public CalendarApplication() {
        calendarManager = new CalendarManager(); // Fixed: Implemented CalendarManager
        reminderService = new ReminderService(); // Fixed: Implemented ReminderService
        createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Calendar Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Event display
        JTextArea eventDisplay = new JTextArea();
        eventDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventDisplay);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel for adding events
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel dateTimeLabel = new JLabel("Event Date & Time (yyyy-MM-dd HH:mm): ");
        JTextField dateTimeField = new JTextField();
        JLabel descriptionLabel = new JLabel("Event Description: ");
        JTextField descriptionField = new JTextField();
        JButton addButton = new JButton("Add Event");

        inputPanel.add(dateTimeLabel);
        inputPanel.add(dateTimeField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel()); // Placeholder
        inputPanel.add(addButton);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Calendar panel
        calendarPanel = new JPanel();
        updateCalendar(LocalDate.now());

        // Layout for calendar and event display
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, calendarPanel, mainPanel);
        splitPane.setDividerLocation(300);
        frame.add(splitPane);

        // Button action for adding events
        addButton.addActionListener(e -> {
            String dateTimeStr = dateTimeField.getText();
            String description = descriptionField.getText();
            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // Fixed: Utility.parseDateTime replaced
                Event event = new Event(dateTime, description); // Fixed: Correct Event constructor used
                calendarManager.addEvent(event);
                reminderService.scheduleReminder(event);
                updateEventDisplay(eventDisplay);
                dateTimeField.setText("");
                descriptionField.setText("");
                JOptionPane.showMessageDialog(frame, "Event added successfully.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date/time format. Please use yyyy-MM-dd HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Load existing events
        updateEventDisplay(eventDisplay);

        frame.setVisible(true);
    }

    private void updateEventDisplay(JTextArea eventDisplay) {
        StringBuilder displayText = new StringBuilder("Upcoming Events:\n");
        for (Event event : calendarManager.getEvents()) {
            displayText.append(event).append("\n");
        }
        eventDisplay.setText(displayText.toString());
    }

    private void updateCalendar(LocalDate currentDate) {
        calendarPanel.removeAll();
        calendarPanel.setLayout(new BorderLayout());

        // Header with month navigation
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        JLabel monthLabel = new JLabel(currentDate.getMonth() + " " + currentDate.getYear(), SwingConstants.CENTER);

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        calendarPanel.add(headerPanel, BorderLayout.NORTH);

        // Calendar grid
        JPanel daysPanel = new JPanel(new GridLayout(7, 7)); // 7 days in a week
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            daysPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonthValue());
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        int startDay = firstDayOfMonth.getDayOfWeek().getValue() % 7; // 0=Sunday, 6=Saturday

        // Fill empty cells before the first day of the month
        for (int i = 0; i < startDay; i++) {
            daysPanel.add(new JLabel());
        }

        // Fill days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            int finalDay = day; // Fixed: Local variable issue
            JButton dayButton = new JButton(String.valueOf(day));
            daysPanel.add(dayButton);

            // Highlight today's date
            if (currentDate.getDayOfMonth() == day) {
                dayButton.setBackground(Color.CYAN);
            }

            // Event on click
            dayButton.addActionListener(e -> JOptionPane.showMessageDialog(calendarPanel, "Selected date: " + currentDate.withDayOfMonth(finalDay)));
        }

        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        // Month navigation actions
        prevButton.addActionListener(e -> updateCalendar(currentDate.minusMonths(1)));
        nextButton.addActionListener(e -> updateCalendar(currentDate.plusMonths(1)));

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarApplication::new);
    }
}






