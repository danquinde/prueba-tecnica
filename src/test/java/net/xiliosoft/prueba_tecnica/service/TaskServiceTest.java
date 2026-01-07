package net.xiliosoft.prueba_tecnica.service;

import net.xiliosoft.prueba_tecnica.model.Task;
import net.xiliosoft.prueba_tecnica.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    @Before
    public void setUp() {
        testTask = new Task("Prueba", "Descripcion ", new Date());
        testTask.setId(1L);
    }

    @Test
    public void testCreateTask() {
        // Arrange
        doNothing().when(taskRepository).save(testTask);

        // Act
        taskService.createTask(testTask);

        // Assert
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    public void testUpdateTask() {
        // Arrange
        testTask.setTitle("Actualizar Tarea");
        doNothing().when(taskRepository).save(testTask);

        // Act
        taskService.updateTask(testTask);

        // Assert
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    public void testDeleteTask() {
        // Arrange
        Long taskId = 1L;
        doNothing().when(taskRepository).delete(taskId);

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskRepository, times(1)).delete(taskId);
    }

    @Test
    public void testGetAllTasks() {
        // Arrange
        Task task2 = new Task("Tarea 2", "Descripcion 2", new Date());
        task2.setId(2L);
        List<Task> expectedTasks = Arrays.asList(testTask, task2);
        when(taskRepository.findAll()).thenReturn(expectedTasks);

        // Act
        List<Task> actualTasks = taskService.getAllTasks();

        // Assert
        assertNotNull(actualTasks);
        assertEquals(2, actualTasks.size());
        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testMarkAsCompleted_TaskExists() {
        // Arrange
        Long taskId = 1L;
        testTask.setCompleted(false);
        when(taskRepository.findById(taskId)).thenReturn(testTask);
        doNothing().when(taskRepository).save(testTask);

        // Act
        taskService.markAsCompleted(taskId);

        // Assert
        assertTrue(testTask.isCompleted());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    public void testMarkAsCompleted_TaskNotFound() {
        // Arrange
        Long taskId = 999L;
        when(taskRepository.findById(taskId)).thenReturn(null);

        // Act
        taskService.markAsCompleted(taskId);

        // Assert
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any(Task.class));
    }
}
