package com.example.lab2.main;

import com.example.lab2.controllers.BaseController;
import com.example.lab2.Helpers.ProjectIOHelper;
import com.example.lab2.model.Project;
import com.example.lab2.model.Task;

import java.util.List;


public class Context {
    private List<Project> projects;
    private List<Task> tasks;
    private Project currentProject;
    private Task currentTask;
    private BaseController state;

    public void setState(BaseController state) {
        this.state = state;
    }
    public BaseController getState() {
       return state;
    }

    public Context() {
       projects = ProjectIOHelper.readProjectsFromJson();
    }

    public void save(){
        ProjectIOHelper.writeProjectsToJson(projects);
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
