package com.example.ProjectTracker.Project;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {


    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void canGetAllProjects() {
        //when
        projectService.getAllProjects();
        //then
        verify(projectRepository).findAll();
    }

    @Test
    @Disabled
    void canGetProjectById() {
        //when
        Project project = new Project(1L, "test project", 200);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        Project expected = projectService.findById(project.getId()).orElse(null);

        assertThat(expected).isSameAs(project);

    }

    @Test
    @Disabled
    void findByProjectNameContaining() {
    }

    @Test
    @Disabled
    void addProject() {
    }

    @Test
    @Disabled
    void deleteProject() {
    }

    @Test
    @Disabled
    void updateProject() {
    }

    @Test
    @Disabled
    void findById() {
    }
}