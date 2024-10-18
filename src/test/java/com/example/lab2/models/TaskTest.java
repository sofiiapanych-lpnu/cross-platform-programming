package com.example.lab2.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;
    private Task subtask1;
    private Task subtask2;

    @BeforeEach
    void setUp() {
        task = new Task("Main Task", "Description of main task",
                LocalDate.now(), LocalDate.now().plusDays(5),
                TaskPriority.HIGH, TaskStatus.TO_DO);

        subtask1 = new Task("Subtask 1", "Description of subtask 1",
                LocalDate.now(), LocalDate.now().plusDays(2),
                TaskPriority.MEDIUM, TaskStatus.IN_PROGRESS);

        subtask2 = new Task("Subtask 2", "Description of subtask 2",
                LocalDate.now(), LocalDate.now().plusDays(3),
                TaskPriority.LOW, TaskStatus.TO_DO);
    }

    @Test
    void testAddSubtask() {
        task.addSubtask(subtask1);
        task.addSubtask(subtask2);

        assertEquals(2, task.getSubtasks().size());
        assertTrue(task.getSubtasks().contains(subtask1));
        assertTrue(task.getSubtasks().contains(subtask2));
    }

    @Test
    void testToStringWithoutSubtasks() {
        String taskInfo = task.toString();

        assertTrue(taskInfo.contains("Task ID: " + task.getId()));
        assertTrue(taskInfo.contains("Title: Main Task"));
        assertTrue(taskInfo.contains("Description: Description of main task"));
        assertTrue(taskInfo.contains("Start Date: " + LocalDate.now().format(Task.formatter)));
        assertTrue(taskInfo.contains("Deadline: " + LocalDate.now().plusDays(5).format(Task.formatter)));
        assertTrue(taskInfo.contains("Priority: HIGH"));
        assertTrue(taskInfo.contains("Status: TO_DO"));
        assertTrue(taskInfo.contains("Subtasks: None"));
    }

    @Test
    void testToStringWithSubtasks() {
        task.addSubtask(subtask1);
        task.addSubtask(subtask2);

        String taskInfo = task.toString();

        assertTrue(taskInfo.contains("Subtasks:"));
        assertTrue(taskInfo.contains("Subtask 1"));
        assertTrue(taskInfo.contains("Subtask 2"));
    }

    @Test
    void testGetMostImportantTasks() {
        Task task1 = new Task("Task 1", "First task",
                LocalDate.now(), LocalDate.now().plusDays(4),
                TaskPriority.LOW, TaskStatus.TO_DO);
        Task task2 = new Task("Task 2", "Second task",
                LocalDate.now(), LocalDate.now().plusDays(1),
                TaskPriority.HIGH, TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Task 3", "Third task",
                LocalDate.now(), LocalDate.now().plusDays(3),
                TaskPriority.MEDIUM, TaskStatus.TO_DO);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        List<Task> sortedTasks = Task.getMostImportantTasks(tasks);

        assertEquals(task2, sortedTasks.get(0));
        assertEquals(task3, sortedTasks.get(1));
        assertEquals(task1, sortedTasks.get(2));
    }

    @Test
    void testSettersAndGetters() {
        task.setTitle("Updated Task");
        task.setDescription("Updated description");
        task.setPriority(TaskPriority.LOW);
        task.setStatus(TaskStatus.DONE);
        task.setStartDate(LocalDate.now().plusDays(1));
        task.setDeadline(LocalDate.now().plusDays(10));

        assertEquals("Updated Task", task.getTitle());
        assertEquals("Updated description", task.getDescription());
        assertEquals(TaskPriority.LOW, task.getPriority());
        assertEquals(TaskStatus.DONE, task.getStatus());
        assertEquals(LocalDate.now().plusDays(1), task.getStartDate());
        assertEquals(LocalDate.now().plusDays(10), task.getDeadline());
    }
}
