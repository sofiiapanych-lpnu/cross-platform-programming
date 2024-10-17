package com.example.lab2.view;

import com.example.lab2.model.Project;
import com.example.lab2.model.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalendarView {
    private Stage stage;
    private LocalDate currentDate;
    private List<Project> projects;
    private GridPane gridPane;

    public CalendarView(List<Project> projects) {
        this.projects = projects;
        this.currentDate = LocalDate.now(); // Встановлюємо сьогоднішню дату
        this.stage = new Stage();
        initialize();
    }

    private void initialize() {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        HBox navigation = new HBox();
        Button prevButton = new Button("<");
        Button nextButton = new Button(">");
        Button currentMonthButton = new Button("Сьогодні");

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

        navigation.getChildren().addAll(prevButton, currentMonthButton, nextButton);
        gridPane.add(navigation, 0, 0, 7, 1); // Додаємо навігаційні кнопки до сітки

        displayCalendar(); // Відображаємо календар
        Scene scene = new Scene(gridPane, 700, 400);
        stage.setTitle("Календар дедлайнів");
        stage.setScene(scene);
        stage.show();
    }

    private void displayCalendar() {
        // Очищення попередніх даних
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 0); // Очищуємо все, крім рядка навігації

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int dayOfWeek = currentDate.withDayOfMonth(1).getDayOfWeek().getValue(); // Понеділок = 1, Неділя = 7
        int daysInMonth = currentDate.lengthOfMonth();

        // Додаємо назви днів тижня
        String[] dayNames = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"};
        for (int i = 0; i < dayNames.length; i++) {
            gridPane.add(new Button(dayNames[i]), i, 1);
        }

        // Заповнюємо календар
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
            Button dateButton = new Button(String.valueOf(day));
            dateButton.setMinSize(100, 50);
            dateButton.setOnAction(event -> displayProjectsAndTasks(date));

            // Додаємо назви проектів або тасків до кнопок (приклад)
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

            // Додаємо кнопку в сітку
            gridPane.add(dateButton, (day + dayOfWeek - 2) % 7, (day + dayOfWeek - 2) / 7 + 2); // Розташування кнопки в сітці
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
