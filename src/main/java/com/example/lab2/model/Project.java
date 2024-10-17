package com.example.lab2.model;
import com.example.lab2.service.IdGenerator;

import java.time.LocalDate;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private List<Task> tasks;
    private LocalDate startDate;
    private LocalDate endDate;

    public Project(String name, LocalDate startDate, LocalDate endDate, List<Task> tasks) {
        this.id = IdGenerator.getInstance().generateProjectId();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
    }
    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getProjectInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("Project: ").append(name).append("\n")
                .append("Id:").append(id).append("\n")
                .append("Start Date: ").append(startDate).append("\n")
                .append("End Date: ").append(endDate).append("\n")
                .append("Tasks:\n");

        if (tasks.isEmpty()) {
            sb.append("  No tasks assigned.\n");
        } else {
            for (Task task : tasks) {
                sb.append("  - ").append(task.toString()).append("\n");
            }
        }

        return sb.toString();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
