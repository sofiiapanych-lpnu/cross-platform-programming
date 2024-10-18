package com.example.lab2.models;
import com.example.lab2.service.IdGenerator;
import com.example.lab2.service.ReminderService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate deadline;
    private TaskPriority priority;
    private TaskStatus status;
    private List<Task> subtasks;

    public Task(String title, String description, LocalDate startDate, LocalDate deadline, TaskPriority priority, TaskStatus status) {
        this.id = IdGenerator.getInstance().generateTaskId();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.subtasks = new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public TaskPriority getPriority() {
        return priority;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public List<Task> getSubtasks() {
        return subtasks;
    }
    public void setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtask(Task subtask) {
        subtasks.add(subtask);
    }

    public static List<Task> getMostImportantTasks(List<Task> tasks) {
        return tasks.stream()
                .peek(task -> task.setSubtasks(
                        task.getSubtasks().stream()
                                .sorted(Comparator
                                        .comparing(Task::getPriority).reversed()
                                        .thenComparing(Task::getDeadline))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator
                        .comparing(Task::getPriority).reversed()
                        .thenComparing(Task::getDeadline))
                .collect(Collectors.toList());
    }

    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Task ID: ").append(id)
                .append("\nTitle: ").append(title)
                .append("\nDescription: ").append(description)
                .append("\nStart Date: ").append(startDate.format(formatter))
                .append("\nDeadline: ").append(deadline.format(formatter))
                .append(" (").append(formatDeadlineStatus(deadline)).append(")")
                .append("\nPriority: ").append(priority)
                .append("\nStatus: ").append(status);

        if (!subtasks.isEmpty()) {
            sb.append("\nSubtasks: ");
            for (Task subtask : subtasks) {
                sb.append("\n  - ").append(subtask.toString().replace("\n", "\n    "));
            }
        } else {
            sb.append("\nSubtasks: None");
        }

        return sb.toString();
    }
    private String formatDeadlineStatus(LocalDate deadline) {
        long daysUntilDeadline = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        return ReminderService.formatDaysUntilDeadline(daysUntilDeadline);
    }
}
