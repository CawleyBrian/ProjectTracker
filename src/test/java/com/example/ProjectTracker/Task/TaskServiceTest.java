package com.example.ProjectTracker.Task;

import com.example.ProjectTracker.Exception.ResourceNotFoundException;
import com.example.ProjectTracker.Project.Project;
import com.example.ProjectTracker.Project.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp(){
        taskService = new TaskService(taskRepository);
    }

    @Test
    void getTasks() {
        taskService.getTasks();
        //then
        verify(taskRepository).findAll(Sort.by(Sort.Direction.ASC, "id"));

    }

    @Test
    void findByProjectId() {
        //create task, mock return of task list.
        Project testProject = new Project(1L, "Project name",123);
        Task task = new Task(99L, "test task","desc", testProject,"bug", 123,123);
        List<Task> taskList = asList(task);

        when(taskRepository.findByProjectId(1L)).thenReturn(asList(task));
        List<Task> result = taskService.findByProjectId(1L);
        assertEquals(task, result.get(0));
    }

    @Test
    void shouldThrowExceptionWhenNoTasksFound() throws Exception{
        //create task, mock return of empty list.
        when(taskRepository.findByProjectId(1L)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> taskService.findByProjectId(1L));
    }

    @Test
    void saveTask() {
        Project testProject = new Project(1L, "Project name",123);
        Task task = new Task(99L, "test task","desc", testProject,"bug", 123,123);

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.saveTask(task);

        verify(taskRepository).save(task);
        assertEquals(result,task);
    }

    @Test
    void deleteTask() {
        Project testProject = new Project(1L, "Project name",123);
        Task task = new Task(99L, "test task","desc", testProject,"bug", 123,123);

        when(taskRepository.existsById(99L)).thenReturn(Boolean.TRUE);

        taskService.deleteTask(99L);

        verify(taskRepository,times(1)).deleteById(99L);

    }

    @Test
    void deleteByProjectId() {
        //Task and project to be deleted
        Project testProject = new Project(1L, "Project name",123);
        Task task = new Task(99L, "test task","desc", testProject,"bug", 123,123);
        //when tasks deleted
        taskService.deleteByProjectId(99L);
        //verify repository action
        verify(taskRepository,times(1)).deleteByProjectId(99L);
    }


    @Test
    void updateTask() {
        String originalName = "task name";
        String originalDesc = "desc";
        Project testProject = new Project(1L, "Project name",123);
        Task task = new Task(99L, originalName,originalDesc, testProject,"bug", 123,123);

        when(taskRepository.findById(99L)).thenReturn(Optional.of(task));

        String updatedName = "updated title";
        String updatedDesc = "updated desc";

        taskService.updateTask(99L, updatedName, updatedDesc);
        assertNotEquals(originalName, updatedName);
        assertNotEquals(originalDesc, updatedDesc);
    }


    @Test
    void addTask() {

        Project testProject = new Project(1L, "Project name",123);
        Task task = new Task(99L, "test task","desc", testProject,"bug", 123,123);

        when(taskRepository.save(task)).thenReturn(task);

        taskService.addTask(task);

        verify(taskRepository).save(task);

    }
}