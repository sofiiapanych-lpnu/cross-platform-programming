package com.example.lab2.controllers;


import com.example.lab2.Helpers.TaskIOHelper;
import com.example.lab2.main.Context;
import com.example.lab2.model.Task;
import com.example.lab2.model.TaskStatus;

import java.util.Optional;
import java.util.Scanner;

public class TaskController extends BaseController{

    public TaskController(Context context) {
        super(context);
    }

    @Override
    public void sendActionsList() {
        System.out.println("1. Вивести всі підзавдання");
        System.out.println("2. Додати підзавдання");
        System.out.println("3. Видалити підзавдання");
        System.out.println("4. Вибрати підзавдання");
        System.out.println("5. Змінити статус");
        System.out.println("6. Повернутись до завдань");
        System.out.println("7. Повернутись до проектів");
    }

    @Override
    public void answerToInputAction(String input) {
        switch (input) {
            case "1":
                System.out.println(context.getCurrentTask().getSubtasks());
                break;
            case "2":
                TaskIOHelper.addSubtasks(context.getCurrentTask());
                System.out.println("\n ----SubtaskAdded---- \n");
                context.save();
                break;
            case "3":
                var subtask = selectSubtask();
                if (subtask.isPresent()){
                    context.getCurrentTask().getSubtasks().remove(subtask.get());
                    context.save();
                    System.out.println("\n ----Subtask deleted---- \n");
                }else{
                    System.out.println("\n ----Subtask was NOT deleted---- \n");
                }
                break;
            case "4":
                var res = selectSubtask();
                if (res.isPresent()){
                    context.setCurrentTask(res.get());
                    System.out.println("\n ----Subtask selected---- \n");
                }else{
                    System.out.println("\n ----Subtask was NOT selected---- \n");
                }
                break;
            case "5":
                Scanner scanner = new Scanner(System.in);
                System.out.println("Select task status (1 - TO_DO, 2 - IN_PROGRESS, 3 - DONE):");
                int statusChoice = scanner.nextInt();
                scanner.nextLine();
                try {
                    TaskStatus newStatus;
                    switch (statusChoice) {
                        case 1:
                            newStatus = TaskStatus.TO_DO;
                            break;
                        case 2:
                            newStatus = TaskStatus.IN_PROGRESS;
                            break;
                        case 3:
                            newStatus = TaskStatus.DONE;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid status choice.");
                    }

                    System.out.println("Selected status: " + newStatus);
                    context.getCurrentTask().setStatus(newStatus);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case "6":
                context.setCurrentTask(null);
                context.setState(new ProjectController(context));
                break;
            case "7":
                context.setCurrentTask(null);
                context.setState(new BaseController(context));
                break;
            default:
                System.out.println("Неправильний вибір. Спробуйте ще раз.");
        }
    }

    private Optional<Task> selectSubtask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter subtask id:");
        int id = scanner.nextInt();
        return context.getCurrentTask().getSubtasks().stream().filter(p -> p.getId() == id).findFirst();
    }
}
