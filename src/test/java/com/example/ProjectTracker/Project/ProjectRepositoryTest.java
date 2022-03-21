package com.example.ProjectTracker.Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
    }

    @Test
    void findByProjectNameContaining() {
        //given -
        Project projectOne = new Project("Project one",200);
        projectRepository.save(projectOne);

        Project projectTwo = new Project("Project two",200);
        projectRepository.save(projectTwo);

        //when
        List<Project> projectList = projectRepository.findByProjectNameContaining("one");
        //then
        assertTrue(projectList.contains(projectOne));
        assertFalse(projectList.contains(projectTwo));
    }
}