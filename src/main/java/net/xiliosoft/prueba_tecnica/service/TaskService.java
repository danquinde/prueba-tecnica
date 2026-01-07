package net.xiliosoft.prueba_tecnica.service;

import net.xiliosoft.prueba_tecnica.model.Task;
import net.xiliosoft.prueba_tecnica.repository.TaskRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TaskService {

    @Inject
    private TaskRepository taskRepository;

    public void createTask(Task task) {
        taskRepository.save(task);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.delete(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void markAsCompleted(Long id) {
        Task task = taskRepository.findById(id);
        if (task != null) {
            task.setCompleted(true);
            taskRepository.save(task);
        }
    }
}
