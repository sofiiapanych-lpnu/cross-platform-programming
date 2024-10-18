package com.example.lab2.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private Project project;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {

        task1 = new Task("Task 1", "First task", LocalDate.now(), LocalDate.now().plusDays(5),
                TaskPriority.MEDIUM, TaskStatus.TO_DO);
        task2 = new Task("Task 2", "Second task", LocalDate.now(), LocalDate.now().plusDays(3),
                TaskPriority.HIGH, TaskStatus.IN_PROGRESS);

        List<Task> initialTasks = new ArrayList<>();
        project = new Project("Test Project", LocalDate.now(), LocalDate.now().plusDays(10), initialTasks);
        int projectId = 1;
        project.setId(projectId);
    }

    @Test
    void testAddTask() {
        assertTrue(project.getTasks().isEmpty());

        project.addTask(task1);
        assertEquals(1, project.getTasks().size());
        assertEquals(task1, project.getTasks().getFirst());

        project.addTask(task2);
        assertEquals(2, project.getTasks().size());
        assertEquals(task2, project.getTasks().get(1));
    }

    @Test
    void testGetProjectInfo() {
        task1.setId(22);
        task2.setId(23);

        project.addTask(task1);
        project.addTask(task2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String task1StartDateFormatted = task1.getStartDate().format(formatter);
        String task1DeadlineFormatted = task1.getDeadline().format(formatter);
        String task2StartDateFormatted = task2.getStartDate().format(formatter);
        String task2DeadlineFormatted = task2.getDeadline().format(formatter);

        long task1DaysLeft = ChronoUnit.DAYS.between(LocalDate.now(), task1.getDeadline());
        long task2DaysLeft = ChronoUnit.DAYS.between(LocalDate.now(), task2.getDeadline());

        String expectedInfo = "Project: Test Project\n" +
                "Id:1\n" +
                "Start Date: " + LocalDate.now() + "\n" +
                "End Date: " + LocalDate.now().plusDays(10) + "\n" +
                "Tasks:\n" +
                "  - Task ID: 22\n" +
                "Title: Task 1\n" +
                "Description: First task\n" +
                "Start Date: " + task1StartDateFormatted + "\n" +
                "Deadline: " + task1DeadlineFormatted + " (" + task1DaysLeft + " days left)\n" +
                "Priority: MEDIUM\n" +
                "Status: TO_DO\n" +
                "Subtasks: None\n" +
                "  - Task ID: 23\n" +
                "Title: Task 2\n" +
                "Description: Second task\n" +
                "Start Date: " + task2StartDateFormatted + "\n" +
                "Deadline: " + task2DeadlineFormatted + " (" + task2DaysLeft + " days left)\n" +
                "Priority: HIGH\n" +
                "Status: IN_PROGRESS\n" +
                "Subtasks: None\n";

        assertEquals(expectedInfo, project.getProjectInfo());
    }


    @Test
    void testGetId() {
        assertEquals(1, project.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Test Project", project.getName());

        project.setName("Updated Project");
        assertEquals("Updated Project", project.getName());
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = LocalDate.now();
        assertEquals(startDate, project.getStartDate());

        LocalDate newStartDate = startDate.plusDays(1);
        project.setStartDate(newStartDate);
        assertEquals(newStartDate, project.getStartDate());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = LocalDate.now().plusDays(10);
        assertEquals(endDate, project.getEndDate());

        LocalDate newEndDate = endDate.plusDays(5);
        project.setEndDate(newEndDate);
        assertEquals(newEndDate, project.getEndDate());
    }
}
