package com.example.lab2.service;

import com.example.lab2.models.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReminderService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void checkDeadlines(List<Task> tasks) {
        LocalDate today = LocalDate.now();
        System.out.println("\n==================== DEADLINES =====================");

        for (Task task : tasks) {
            displayTaskDeadline(task, today);
            System.out.println("====================================================\n");
        }
    }

    private static void displayTaskDeadline(Task task, LocalDate today) {
        long daysUntilDeadline = ChronoUnit.DAYS.between(today, task.getDeadline());
        System.out.println("Task: " + task.getTitle());
        System.out.println("Deadline: " + task.getDeadline().format(DATE_FORMATTER) + " (" + formatDaysUntilDeadline(daysUntilDeadline) + ")");
        displayDeadlineStatus(daysUntilDeadline);

        if (!task.getSubtasks().isEmpty()) {
            System.out.println("  Subtasks:");
            for (Task subtask : task.getSubtasks()) {
                displaySubtaskDeadline(subtask, today);
            }
        }
    }

    private static void displaySubtaskDeadline(Task subtask, LocalDate today) {
        long subtaskDaysUntilDeadline = ChronoUnit.DAYS.between(today, subtask.getDeadline());
        System.out.println("  --------------------------------------------------");
        System.out.println("  Subtask: " + subtask.getTitle());
        System.out.println("  Deadline: " + subtask.getDeadline().format(DATE_FORMATTER) + " (" + formatDaysUntilDeadline(subtaskDaysUntilDeadline) + ")");
        displayDeadlineStatus(subtaskDaysUntilDeadline);
    }

    private static void displayDeadlineStatus(long daysUntilDeadline) {
        if (daysUntilDeadline == 0) {
            System.out.println("⚠️ Reminder: Today is the deadline!");
        } else if (daysUntilDeadline == 1) {
            System.out.println("⚠️ Reminder: The deadline is tomorrow!");
        } else if (daysUntilDeadline < 0) {
            System.out.println("❌ The deadline has already passed.");
        } else {
            System.out.println("✅ You still have " + daysUntilDeadline + " days left.");
        }
    }

    public static String formatDaysUntilDeadline(long daysUntilDeadline) {
        if (daysUntilDeadline == 0) {
            return "today";
        } else if (daysUntilDeadline == 1) {
            return "tomorrow";
        } else if (daysUntilDeadline < 0) {
            return Math.abs(daysUntilDeadline) + " days ago";
        } else {
            return daysUntilDeadline + " days left";
        }
    }
}
