package net.xiliosoft.prueba_tecnica.controller;

import net.xiliosoft.prueba_tecnica.model.Task;
import net.xiliosoft.prueba_tecnica.service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TaskController implements Serializable {

    @Inject
    private TaskService taskService;

    private Task newTask;
    private Task selectedTask;
    private List<Task> tasks;

    @PostConstruct
    public void init() {
        newTask = new Task();
        loadTasks();
    }

    public void loadTasks() {
        tasks = taskService.getAllTasks();
    }

    public void createTask() {
        try {
            taskService.createTask(newTask);
            newTask = new Task();
            loadTasks();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tarea creada correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la tarea: " + e.getMessage()));
        }
    }

    public void deleteTask(Long id) {
        try {
            taskService.deleteTask(id);
            loadTasks();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tarea eliminada correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la tarea"));
        }
    }

    public void completeTask(Long id) {
        try {
            taskService.markAsCompleted(id);
            loadTasks();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tarea marcada como completada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo completar la tarea"));
        }
    }

    // Getters and Setters
    public Task getNewTask() {
        return newTask;
    }

    public void setNewTask(Task newTask) {
        this.newTask = newTask;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
}
