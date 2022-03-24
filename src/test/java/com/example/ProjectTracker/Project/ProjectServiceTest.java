package com.example.ProjectTracker.Project;

import com.example.ProjectTracker.Exception.ResourceNotFoundException;
import org.assertj.core.api.AssertJProxySetup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    void canGetProjectById() {
        //when
        Project project = new Project(1L, "test project", 200);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        Project expected = projectService.findById(1l).orElse(null);

        assertEquals(expected, project);

    }

    @Test
    void findProjectsWithNameContainingTest() {

        Project project = new Project();
        project.setProjectName("test project");
        when(projectRepository.findByProjectNameContaining("test"))
                .thenReturn(asList(project));

        List<Project> result = projectService.findByProjectNameContaining("test");

        assertEquals(project, result.get(0));
        verify(projectRepository).findByProjectNameContaining("test");

    }

    @Test
    void canAddProject() {

        Project project = new Project(1L, "test project", 200);
        when(projectRepository.save(project)).thenReturn(project);

        Project result = projectService.addProject(project);

        assertEquals(project,result);

    }

    @Test
    void canDeleteProject() {

        Project project = new Project(1L, "test project", 200);

        when(projectRepository.existsById(1L)).thenReturn(Boolean.TRUE);

        projectService.deleteProject(project.getId());

        verify(projectRepository, times(1))
                .deleteById(1L);

    }

    @Test
    void canNotDeleteWithInvalidId() throws ResourceNotFoundException {

        when(projectRepository.existsById(1L)).thenReturn(Boolean.FALSE);
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> projectService.deleteProject(1L));


    }


    @Test
    void canUpdateProjectNameAndBudget() {
        Project project = new Project(1L, "test project", 200);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        String originalName = project.getProjectName();
        int originalBudget = project.getBudget();

        projectService.updateProject(1L,"new project name",50);

        String updatedName = project.getProjectName();
        int updatedBudget = project.getBudget();

        assertNotEquals(updatedName, originalName);
        assertNotEquals(updatedBudget,originalBudget);

    }

    @Test
    void canNotUpdateWithInvalidId() throws ResourceNotFoundException {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> projectService.updateProject(1L,"Test", 100));
    }


}