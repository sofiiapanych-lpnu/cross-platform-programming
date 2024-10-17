package com.example.lab2.Helpers;

import com.example.lab2.main.LocalDateAdapter;
import com.example.lab2.model.Project;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectIOHelper {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();
    private static final String FILE_PATH = "projects.json";

    public static Project createProject() {
        System.out.println("Enter project name:");
        String name = scanner.nextLine();

        LocalDate startDate = getDateInput("Enter project start date (dd-MM-yyyy):");
        LocalDate endDate = getDateInput("Enter project end date (dd-MM-yyyy):");

        Project project = new Project(name, startDate, endDate, new ArrayList<>());

        System.out.println("Project created: " + project.getName());

        return project;
    }

    private static LocalDate getDateInput(String prompt) {
        LocalDate date = null;
        boolean valid = false;

        while (!valid) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            try {
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Використовуємо форматтер з TaskInputController
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        return date;
    }

    public static void printProjectInfo(Project project) {
        System.out.println("Project Information:");
        System.out.println(project.getProjectInfo());
    }

    public static void writeProjectsToJson(List<Project> projects) {
//        List<Project> existingProjects = readProjectsFromJson();
//
//        List<Project> allProjects = new ArrayList<>(existingProjects);
//        allProjects.addAll(projects);
        String json = gson.toJson(projects);

        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(json);
            System.out.println("Projects successfully written to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Project> readProjectsFromJson() {
        List<Project> projects = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String firstLine = bufferedReader.readLine();

            if (firstLine == null) {
                return projects;
            }

            try (FileReader reader = new FileReader(FILE_PATH)) {
                Project[] projectsArray = gson.fromJson(reader, Project[].class);

                if (projectsArray != null && projectsArray.length > 0) {
                    projects = new ArrayList<>(List.of(projectsArray));
                } else {
                    System.out.println("No projects found in the JSON file.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

}
