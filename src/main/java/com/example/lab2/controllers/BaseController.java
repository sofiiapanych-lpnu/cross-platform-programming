package com.example.lab2.controllers;


import com.example.lab2.helpers.ProjectIOHelper;
import com.example.lab2.main.Context;
import com.example.lab2.models.Project;

import java.util.*;

public class BaseController {
    protected Context context;

    public BaseController(Context context) {
        this.context = context;
    }

    public void sendActionsList(){
        System.out.println("\nВиберіть дію:");
        System.out.println("1. Переглянути список проектів");
        System.out.println("2. Додати проект");
        System.out.println("3. Видалити проект");
        System.out.println("4. Вибрати проект");
        System.out.println("exit  Вийти");
    }

    public void answerToInputAction(String input){
        switch (input) {
            case "1":
                var projects = context.getProjects();
                projects.stream().forEach(ProjectIOHelper::printProjectInfo);
                break;
            case "2":
                context.getProjects().add(ProjectIOHelper.createProject());
                context.save();
                break;
            case "3":
                var projectToDel = selectProject();
                if(projectToDel.isPresent()){
                    context.getProjects().remove(projectToDel.get());
                    context.save();
                    System.out.println("\n ----Project deleted---- \n");
                }else{
                    System.out.println("\n ----Project was NOT found---- \n");
                }
                break;
            case "4":
                var res = selectProject();
                if (res.isPresent()){
                    context.setCurrentProject(res.get());
                    context.setState(new ProjectController(context));
                    System.out.println("\n ----Project selected---- \n");
                }else{
                    System.out.println("\n ----Project was NOT selected---- \n");
                }
                break;
            default:
                System.out.println("Неправильний вибір. Спробуйте ще раз.");
        }
    }

    public Optional<Project> selectProject(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter project id:");
        int id = scanner.nextInt();
        return context.getProjects().stream().filter(p -> p.getId() == id).findFirst();
    }
}
