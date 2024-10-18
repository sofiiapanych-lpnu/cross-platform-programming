package com.example.lab2.helpers;

import com.example.lab2.models.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectIOHelperTest {

    private final String testFilePath = "projects_test.json";

    @BeforeEach
    void setUp() throws IOException {
        File tempFile = new File(testFilePath);
        tempFile.createNewFile();
        ProjectIOHelper.setFilePath(testFilePath);
    }

    @AfterEach
    void tearDown() {
        File tempFile = new File(testFilePath);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testWriteEmptyProjectList() throws IOException {
        ProjectIOHelper.writeProjectsToJson(new ArrayList<>(), testFilePath);

        List<Project> readProjects = ProjectIOHelper.readProjectsFromJson(testFilePath);

        assertTrue(readProjects.isEmpty(), "Read project list should be empty.");
    }

    @Test
    void testReadProjectsFromEmptyFile() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write(""); // Очищаємо файл
        }

        List<Project> readProjects = ProjectIOHelper.readProjectsFromJson(testFilePath);

        assertTrue(readProjects.isEmpty());
    }
    @Test
    void testWriteAndReadProjectsToJson() throws IOException {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("Project A", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new ArrayList<>()));
        projects.add(new Project("Project B", LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 15), new ArrayList<>()));

        ProjectIOHelper.writeProjectsToJson(projects, testFilePath);

        List<Project> readProjects = ProjectIOHelper.readProjectsFromJson(testFilePath);

        assertEquals(2, readProjects.size(), "Should read two projects from the file.");
        assertEquals("Project A", readProjects.get(0).getName(), "First project name should match.");
        assertEquals("Project B", readProjects.get(1).getName(), "Second project name should match.");
    }

    @Test
    void testPrintProjectInfo() {
        Project project = new Project("Test Project", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new ArrayList<>());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ProjectIOHelper.printProjectInfo(project);

        String output = outContent.toString();
        assertTrue(output.contains("Test Project"), "Output should contain the project name.");
    }
}
