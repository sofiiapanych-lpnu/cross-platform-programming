package com.example.lab2.service;

import com.example.lab2.helpers.ProjectIOHelper;
import com.example.lab2.models.Project;
import com.example.lab2.models.Task;

import java.util.List;
import java.util.stream.Stream;

public class IdGenerator { //singleton
    private static IdGenerator instance;
    private int currentTaskId;
    private int currentProjectId;

    private IdGenerator() {
        currentTaskId = loadMaxTaskId() + 1;
        currentProjectId = loadMaxProjectId() + 1;
    }

    // Гарантуємо, що буде створено лише один екземпляр
    public static synchronized IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    public synchronized int generateTaskId() {
        return currentTaskId++;
    }

    public synchronized int generateProjectId() {
        return currentProjectId++;
    }

    private int loadMaxTaskId() {
        List<Project> projects = ProjectIOHelper.readProjectsFromJson("projects.json");
        return projects.stream()
                .flatMap(project -> project.getTasks().stream())
                .flatMap(task -> Stream.concat(Stream.of(task), task.getSubtasks().stream()))
                .mapToInt(Task::getId)
                .max()
                .orElse(0);
    }

    private int loadMaxProjectId() {
        List<Project> projects = ProjectIOHelper.readProjectsFromJson("projects.json");
        return projects.stream()
                .mapToInt(Project::getId)
                .max()
                .orElse(0);
    }
}
