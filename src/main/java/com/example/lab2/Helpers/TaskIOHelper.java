package com.example.lab2.Helpers;

import com.example.lab2.model.Task;
import com.example.lab2.model.TaskPriority;
import com.example.lab2.model.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Scanner;

public class TaskIOHelper {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static Task createTask() {
        System.out.println("Enter task title:");
        String title = scanner.nextLine();

        System.out.println("Enter task description:");
        String description = scanner.nextLine();

        LocalDate startDate = getDateTimeInput("Enter start date and time (dd-MM-yyyy):");
        LocalDate deadline = getDeadlineInput(startDate);
        TaskPriority priority = getPriorityInput();
        TaskStatus status = getStatusInput();

        return new Task(title, description, startDate, deadline, priority, status);
    }

    public static void addSubtasks(Task parentTask) {
        System.out.println("Creating a subtask for: " + parentTask.getTitle());
        System.out.println("Enter subtask title:");
        String title = scanner.nextLine();

        System.out.println("Enter subtask description:");
        String description = scanner.nextLine();

        LocalDate startDate = getDateTimeInput("Enter start date and time (dd-MM-yyyy):");
        LocalDate deadline = getDeadlineInput(startDate);
        TaskPriority priority = getPriorityInput();
        TaskStatus status = getStatusInput();

        Task subtask = new Task(title, description, startDate, deadline, priority, status);
        parentTask.addSubtask(subtask);
        System.out.println("Subtask added!");
    }

    private static LocalDate getDateTimeInput(String prompt) {
        LocalDate dateTime = null;
        boolean valid = true;

        while (valid) {
            System.out.println(prompt);
            String dateInput = scanner.nextLine();
            try {
                dateTime = LocalDate.parse(dateInput, dateTimeFormatter);
                valid = false;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        return dateTime;
    }

    private static LocalDate getDeadlineInput(LocalDate startDate) {
        LocalDate deadline = null;
        boolean valid = true;

        while (valid) {
            System.out.println("Enter task deadline (dd-MM-yyyy):");
            String dateInput = scanner.nextLine();
            try {
                deadline = LocalDate.parse(dateInput, dateTimeFormatter);

                if (deadline.isBefore(startDate)) {
                    System.out.println("Deadline cannot be earlier than the start date. Please try again.");
                } else {
                    valid = false;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        return deadline;
    }

    private static TaskPriority getPriorityInput() {
        System.out.println("Select task priority (1 - LOW, 2 - MEDIUM, 3 - HIGH):");
        int priorityChoice = scanner.nextInt();
        scanner.nextLine();

        switch (priorityChoice) {
            case 1:
                return TaskPriority.LOW;
            case 2:
                return TaskPriority.MEDIUM;
            case 3:
                return TaskPriority.HIGH;
            default:
                System.out.println("Invalid choice, setting priority to MEDIUM by default.");
                return TaskPriority.MEDIUM;
        }
    }

    private static TaskStatus getStatusInput() {
        System.out.println("Select task status (1 - TO_DO, 2 - IN_PROGRESS, 3 - DONE):");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        switch (statusChoice) {
            case 1:
                return TaskStatus.TO_DO;
            case 2:
                return TaskStatus.IN_PROGRESS;
            case 3:
                return TaskStatus.DONE;
            default:
                System.out.println("Invalid choice, setting status to PENDING by default.");
                return TaskStatus.PENDING;
        }
    }

    public static void printMostImportantTasks(List<Task> tasks) {
        List<Task> sortedTasks = Task.getMostImportantTasks(tasks);

        System.out.println("Most important tasks:");
        for (Task task : sortedTasks) {
            System.out.println("Task: " + task.getTitle() + ", Priority: " + task.getPriority() +
                    ", Deadline: " + task.getDeadline());
            if (!task.getSubtasks().isEmpty()) {
                System.out.println("  Subtasks:");
                for (Task subtask : task.getSubtasks()) {
                    System.out.println("    - Subtask: " + subtask.getTitle() + ", Priority: " + subtask.getPriority() +
                            ", Deadline: " + subtask.getDeadline());
                }
            }
        }
    }
}
