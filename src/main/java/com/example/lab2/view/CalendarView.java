package com.example.lab2.view;

import com.example.lab2.models.Project;
import com.example.lab2.models.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class CalendarView {
    private Stage stage;
    private LocalDate currentDate;
    private List<Project> projects;
    private GridPane gridPane;
    private Label monthYearLabel;

    public CalendarView(List<Project> projects) {
        this.projects = projects;
        this.currentDate = LocalDate.now();
        this.stage = new Stage();
        initialize();
    }

    private void initialize() {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        HBox navigation = new HBox();
        Button prevButton = new Button("<");
        Button nextButton = new Button(">");
        Button currentMonthButton = new Button("Сьогодні");
        monthYearLabel = new Label();
        monthYearLabel.setFont(new Font("Arial", 24));
        HBox.setMargin(monthYearLabel, new Insets(0, 20, 0, 20));

        prevButton.setOnAction(event -> {
            currentDate = currentDate.minusMonths(1);
            displayCalendar();
        });

        nextButton.setOnAction(event -> {
            currentDate = currentDate.plusMonths(1);
            displayCalendar();
        });

        currentMonthButton.setOnAction(event -> {
            currentDate = LocalDate.now();
            displayCalendar();
        });

        navigation.setAlignment(Pos.CENTER);
        navigation.getChildren().addAll(prevButton, monthYearLabel, nextButton, currentMonthButton);
        gridPane.add(navigation, 0, 0, 7, 1);

        displayCalendar();
        Scene scene = new Scene(gridPane, 900, 600);
        stage.setTitle("Календар дедлайнів");
        stage.setScene(scene);
        stage.show();
    }

    private void displayCalendar() {
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 0);

        String month = currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("uk"));
        int year = currentDate.getYear();
        monthYearLabel.setText(month + " " + year);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int dayOfWeek = currentDate.withDayOfMonth(1).getDayOfWeek().getValue();
        int daysInMonth = currentDate.lengthOfMonth();

        String[] dayNames = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"};
        for (int i = 0; i < dayNames.length; i++) {
            Label dayLabel = new Label(dayNames[i]);
            dayLabel.setFont(new Font("Arial", 16));
            dayLabel.setAlignment(Pos.CENTER);
            gridPane.add(dayLabel, i, 1);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
            Button dateButton = new Button(String.valueOf(day));
            dateButton.setMinSize(100, 80);
            dateButton.setFont(new Font("Arial", 14));
            dateButton.setOnAction(event -> displayProjectsAndTasks(date));

            for (Project project : projects) {
                if (project.getEndDate().equals(date)) {
                    dateButton.setText(day + "\n" + project.getName());
                }
                for (Task task : project.getTasks()) {
                    if (task.getDeadline().equals(date)) {
                        dateButton.setText(day + "\n" + task.getTitle());
                    }
                }
            }

            gridPane.add(dateButton, (day + dayOfWeek - 2) % 7, (day + dayOfWeek - 2) / 7 + 2);
        }
    }

    private void displayProjectsAndTasks(LocalDate date) {
        StringBuilder details = new StringBuilder("На цю дату є:\n\n");
        boolean hasEvents = false;

        for (Project project : projects) {
            if (project.getEndDate().equals(date)) {
                details.append("Проект: ").append(project.getName()).append("\n");
                hasEvents = true;
            }
            for (Task task : project.getTasks()) {
                if (task.getDeadline().equals(date)) {
                    details.append("Завдання: ").append(task.getTitle()).append("\n");
                    hasEvents = true;
                }
            }
        }

        if (!hasEvents) {
            details.append("Немає подій на цю дату.");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Деталі на " + date);
        alert.setHeaderText(null);
        alert.setContentText(details.toString());
        alert.showAndWait();
    }
}
