package com.example.lab2.controllers;

import com.example.lab2.helpers.TaskIOHelper;
import com.example.lab2.main.Context;
import com.example.lab2.models.Task;
import com.example.lab2.service.ReminderService;

import java.util.*;

public class ProjectController extends BaseController {

    public ProjectController(Context context) {
        super(context);
    }

    @Override
    public void sendActionsList() {
        System.out.println("\n---------------");
        System.out.println("1. Переглянути список завдань");
        System.out.println("2. Додати завдання");
        System.out.println("3. Вибрати завдання");
        System.out.println("4. Видалити завдання");
        System.out.println("5. Вивід дедлайнів всіх завдань");
        System.out.println("6. Вивід завдань за їх важливістю");
        System.out.println("7. Повернутись до проектів");
    }

    @Override
    public void answerToInputAction(String input) {
        switch (input) {
            case "1":
                System.out.println(context.getCurrentProject().getTasks());
                break;
            case "2":
                context.getCurrentProject().addTask(TaskIOHelper.createTask());
                System.out.println("\n ----TaskAdded---- \n");
                context.save();
                break;
            case "3":
                var res = selectTask();
                if (res.isPresent()){
                    context.setCurrentTask(res.get());
                    context.setState(new TaskController(context));
                    System.out.println("\n ----Task selected---- \n");
                }else{
                    System.out.println("\n ----Task was NOT selected---- \n");
                }
                break;
            case "4":
                var task = selectTask();
                if (task.isPresent()){
                    context.getCurrentProject().getTasks().remove(task.get());
                    context.save();
                    System.out.println("\n ----Task deleted---- \n");
                }else{
                    System.out.println("\n ----Task was NOT deleted---- \n");
                }
                break;
            case "5":
                ReminderService.checkDeadlines(context.getCurrentProject().getTasks());
                break;
            case "6":
                TaskIOHelper.printMostImportantTasks(context.getCurrentProject().getTasks());
                break;
            case "7":
                context.setCurrentProject(null);
                context.setState(new BaseController(context));
                break;
            default:
                System.out.println("Неправильний вибір. Спробуйте ще раз.");
        }
    }


    public Optional<Task> selectTask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task id:");
        int id = scanner.nextInt();
        return context.getCurrentProject().getTasks().stream().filter(p -> p.getId() == id).findFirst();
    }

}
