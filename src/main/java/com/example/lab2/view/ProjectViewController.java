package com.example.lab2.view;

import com.example.lab2.helpers.ProjectIOHelper;
import com.example.lab2.models.Project;
import com.example.lab2.models.Task;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ProjectViewController extends Application {

    private BarChart<String, Number> projectDeadlineGraph;
    private BarChart<String, Number> taskDeadlineGraph;
    private PieChart taskStatusPieChart;
    private BarChart<String, Number> subtaskDeadlineGraph;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        projectDeadlineGraph = new BarChart<>(new CategoryAxis(), new NumberAxis());
        taskDeadlineGraph = new BarChart<>(new CategoryAxis(), new NumberAxis());
        subtaskDeadlineGraph = new BarChart<>(new CategoryAxis(), new NumberAxis());
        taskStatusPieChart = new PieChart();

        projectDeadlineGraph.setTitle("Проекти - Дні до дедлайну");
        taskDeadlineGraph.setTitle("Дедлайни завдань");
        subtaskDeadlineGraph.setTitle("Дедлайни підзавдань");
        taskStatusPieChart.setTitle("Статус завдань");

        List<Project> projects = ProjectIOHelper.readProjectsFromJson("projects.json");

        displayProjectDeadlineGraph(projects);

        Button calendarButton = new Button("Відкрити календар");
        calendarButton.setOnAction(event -> openCalendar(projects));

        VBox vbox = new VBox(calendarButton, projectDeadlineGraph, taskDeadlineGraph, subtaskDeadlineGraph, taskStatusPieChart);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 1000, 800);

        primaryStage.setTitle("Проекти та завдання");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openCalendar(List<Project> projects) {
        new CalendarView(projects);
    }

    private void displayProjectDeadlineGraph(List<Project> projects) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Кількість днів до дедлайну");

        for (Project project : projects) {
            long daysToDeadline = ChronoUnit.DAYS.between(LocalDate.now(), project.getEndDate());
            series.getData().add(new XYChart.Data<>(project.getName(), daysToDeadline));
        }

        projectDeadlineGraph.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setOnMouseClicked(event -> {
                String selectedProjectTitle = data.getXValue();
                Project selectedProject = projects.stream()
                        .filter(project -> project.getName().equals(selectedProjectTitle))
                        .findFirst()
                        .orElse(null);

                if (selectedProject != null) {
                    displayProjectDetails(selectedProject);
                }
            });
        }
    }

    private void displayProjectDetails(Project project) {
        taskDeadlineGraph.getData().clear();
        subtaskDeadlineGraph.getData().clear();
        taskStatusPieChart.getData().clear();

        XYChart.Series<String, Number> deadlineSeries = new XYChart.Series<>();
        deadlineSeries.setName("Дедлайни завдань");

        for (Task task : project.getTasks()) {
            long daysToDeadline = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());
            XYChart.Data<String, Number> taskData = new XYChart.Data<>(task.getTitle(), daysToDeadline);
            deadlineSeries.getData().add(taskData);
        }

        taskDeadlineGraph.getData().add(deadlineSeries);

        for (XYChart.Data<String, Number> data : deadlineSeries.getData()) {
            data.getNode().setOnMouseClicked(event -> {
                Task selectedTask = project.getTasks().stream()
                        .filter(task -> task.getTitle().equals(data.getXValue()))
                        .findFirst()
                        .orElse(null);
                if (selectedTask != null) {
                    displaySubtasks(selectedTask);
                }
            });
        }

        for (Task task : project.getTasks()) {
            PieChart.Data slice = new PieChart.Data(task.getTitle() + " - " + task.getStatus(), 1);
            taskStatusPieChart.getData().add(slice);
        }
    }

    private void displaySubtasks(Task task) {
        subtaskDeadlineGraph.getData().clear();

        XYChart.Series<String, Number> subtaskSeries = new XYChart.Series<>();
        subtaskSeries.setName("Дедлайни підзавдань");

        for (Task subtask : task.getSubtasks()) {
            long daysToSubtaskDeadline = ChronoUnit.DAYS.between(LocalDate.now(), subtask.getDeadline());
            subtaskSeries.getData().add(new XYChart.Data<>(subtask.getTitle(), daysToSubtaskDeadline));

            PieChart.Data subtaskSlice = new PieChart.Data(subtask.getTitle() + " - " + subtask.getStatus(), 1);
            taskStatusPieChart.getData().add(subtaskSlice);
        }

        subtaskDeadlineGraph.getData().add(subtaskSeries);
    }
}
