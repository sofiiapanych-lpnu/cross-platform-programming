package com.example.lab2.main;

import com.example.lab2.controllers.BaseController;
import com.example.lab2.helpers.ProjectIOHelper;
import com.example.lab2.models.Project;
import com.example.lab2.models.Task;

import java.util.List;


public class Context {
    private List<Project> projects;
    private List<Task> tasks;
    private Project currentProject;
    private Task currentTask;
    private BaseController state;
    private static String FILE_PATH = "projects.json";

    public void setState(BaseController state) {
        this.state = state;
    }
    public BaseController getState() {
       return state;
    }

    public Context() {
       projects = ProjectIOHelper.readProjectsFromJson(FILE_PATH);
    }

    public void save(){
        ProjectIOHelper.setFilePath(FILE_PATH);
        ProjectIOHelper.writeProjectsToJson(projects, FILE_PATH);
    }

    public List<Project> getProjects() {
        return projects;
    }
    public Project getCurrentProject() {
        return currentProject;
    }
    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public List<Task> getTasks() {return tasks;}
    public Task getCurrentTask() {
        return currentTask;
    }
    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
}
