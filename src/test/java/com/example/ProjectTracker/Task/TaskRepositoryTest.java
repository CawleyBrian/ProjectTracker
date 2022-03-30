package com.example.ProjectTracker.Task;

import com.example.ProjectTracker.Project.Project;
import com.example.ProjectTracker.Project.ProjectRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp(){
        //Project to be used as foreign key for Tasks.
        Project testProject = new Project(1L, "Project name",123);
        projectRepository.save(testProject);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    void findTasksByProjectId() {
        //Set up two tasks with the same project.
        Project project = projectRepository.getById(1L);

        Task taskOne = new Task(1L,"task one","desc one",project,"Bug",10,0);
        Task taskTwo = new Task(2L,"task two","desc two",project,"Bug",10,0);

        taskRepository.save(taskOne);
        taskRepository.save(taskTwo);

        //Return task list and check it contains both tasks
        List<Task> taskList = taskRepository.findByProjectId(1L);

        assertTrue(taskList.size()==2);
        assertTrue(taskList.get(0).getProject().getId() == 1L);
    }

    @Test
    void deleteByProjectId() {
        //Create task to be deleted.
        Project project = projectRepository.getById(1L);
        Task taskOne = new Task();
        taskOne.setProject(project);
        taskRepository.save(taskOne);

        //taskList will contain task created above
        List<Task> taskList = taskRepository.findByProjectId(1L);
        taskRepository.deleteByProjectId(1L);
        //result will be empty after deleting tasks with project id 1L
        List<Task> result = taskRepository.findByProjectId(1L);

        //Verify first list is not empty and second list is empty after deleting.
        assertFalse(taskList.isEmpty());
        assertTrue(result.isEmpty());
    }
}