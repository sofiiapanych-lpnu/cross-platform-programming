package com.example.task2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class main {
    public static void main(String[] args) {
        var task2 = new Task2("src/main/resources/input.txt");
        task2.readTextFromFile();
        System.out.println(task2.SelectAllTrio());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd-hh-mm-ss");
//        LocalDateTime now = LocalDateTime.now();
//        String date = now.format(formatter);
//        System.out.println(date);

    }
}