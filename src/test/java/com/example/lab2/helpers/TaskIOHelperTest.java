package com.example.lab2.helpers;

import com.example.lab2.models.Task;
import com.example.lab2.models.TaskPriority;
import com.example.lab2.models.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskIOHelperTest {

    @BeforeEach
    void setUp() {
        // Clear any required state before each test if necessary
    }

//    @Test
//    void testCreateTask() {
//        // Simulate user input for creating a task
//        String input = "Test Task\nThis is a test task.\n12-12-2024\n15-12-2024\n1\n1\n"; // Title, Description, Start Date, Deadline, Priority, Status
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        Task task = TaskIOHelper.createTask();
//
//        // Verify task details
//        assertNotNull(task, "Task should not be null.");
//        assertEquals("Test Task", task.getTitle(), "Task title should match input.");
//        assertEquals("This is a test task.", task.getDescription(), "Task description should match input.");
//        assertEquals(LocalDate.of(2024, 12, 12), task.getStartDate(), "Start date should match input.");
//        assertEquals(LocalDate.of(2024, 12, 15), task.getDeadline(), "Deadline should match input.");
//        assertEquals(TaskPriority.LOW, task.getPriority(), "Priority should match input.");
//        assertEquals(TaskStatus.TO_DO, task.getStatus(), "Status should match input.");
//    }
//
//    @Test
//    void testAddSubtasks() {
//        // Create a parent task
//        Task parentTask = new Task("Parent Task", "This is a parent task.", LocalDate.now(), LocalDate.now().plusDays(1), TaskPriority.MEDIUM, TaskStatus.TO_DO);
//
//        // Simulate user input for adding a subtask
//        String input = "Subtask 1\nThis is a subtask.\n13-12-2024\n14-12-2024\n2\n2\n"; // Title, Description, Start Date, Deadline, Priority, Status
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        TaskIOHelper.addSubtasks(parentTask);
//
//        // Verify subtask details
//        assertEquals(1, parentTask.getSubtasks().size(), "Parent task should have one subtask.");
//        Task subtask = parentTask.getSubtasks().get(0);
//        assertEquals("Subtask 1", subtask.getTitle(), "Subtask title should match input.");
//        assertEquals("This is a subtask.", subtask.getDescription(), "Subtask description should match input.");
//        assertEquals(LocalDate.of(2024, 12, 13), subtask.getStartDate(), "Subtask start date should match input.");
//        assertEquals(LocalDate.of(2024, 12, 14), subtask.getDeadline(), "Subtask deadline should match input.");
//        assertEquals(TaskPriority.MEDIUM, subtask.getPriority(), "Subtask priority should match input.");
//        assertEquals(TaskStatus.IN_PROGRESS, subtask.getStatus(), "Subtask status should match input.");
//    }



    @Test
    void testPrintMostImportantTasks() throws IOException {
        // Create the required JSON file
        Path jsonPath = Paths.get("projects.json");
        Files.write(jsonPath, "[]".getBytes()); // Write an empty JSON array

        // Create some tasks
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task("Important Task 1", "Description 1", LocalDate.now(), LocalDate.now().plusDays(1), TaskPriority.HIGH, TaskStatus.TO_DO);
        Task task2 = new Task("Important Task 2", "Description 2", LocalDate.now(), LocalDate.now().plusDays(2), TaskPriority.LOW, TaskStatus.IN_PROGRESS);
        tasks.add(task1);
        tasks.add(task2);

        // Redirect output to capture it
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TaskIOHelper.printMostImportantTasks(tasks);

        // Capture and assert output
        String output = outContent.toString();
        String expectedOutput = "Most important tasks:\n" +
                "Task: Important Task 1, Priority: HIGH, Deadline: " + task1.getDeadline() + "\n" +
                "Task: Important Task 2, Priority: LOW, Deadline: " + task2.getDeadline() + "\n";

        assertTrue(output.contains("Most important tasks:"), "Output should contain 'Most important tasks:'");
        assertTrue(output.contains("Task: Important Task 1"), "Output should contain 'Important Task 1'");
        assertTrue(output.contains("Task: Important Task 2"), "Output should contain 'Important Task 2'");

        // Clean up
        Files.deleteIfExists(jsonPath); // Remove the file after test
    }

}
