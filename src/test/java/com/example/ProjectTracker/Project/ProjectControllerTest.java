package com.example.ProjectTracker.Project;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;




    @Test
    void canGetAllProjects() throws Exception {
        //Create two projects, add as list.
        Project projectOne = new Project(1L, "test project", 200);
        Project projectTwo = new Project(2L, "test project two", 200);
        List<Project> projectList = new ArrayList<>(Arrays.asList(projectOne,projectTwo));

        //Mock service should return list
        given(projectService.getAllProjects()).willReturn(projectList);


        RequestBuilder request = get("/api/v1/project/projects")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].projectName", Matchers.is(projectOne.getProjectName())));

    }

    @Test
    void canGetProjectsByName() throws Exception {
        //Create two projects, add as list.
        Project projectOne = new Project(1L, "test project", 200);
        Project projectTwo = new Project(2L, "test project two", 200);
        List<Project> projectList = new ArrayList<>(Arrays.asList(projectOne,projectTwo));

        //Mock service should return list
        given(projectService.findByProjectNameContaining("test")).willReturn(projectList);


        RequestBuilder request = get("/api/v1/project/projects?projectName=test")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].projectName", Matchers.is(projectOne.getProjectName())));
    }

    @Test
    void willReturnEmptyResponse() throws Exception {
        //Create empty list
        List<Project> projectList = new ArrayList<>();

        //Mock service should return list
        given(projectService.findByProjectNameContaining("test")).willReturn(projectList);


        RequestBuilder request = get("/api/v1/project/projects?projectName=test")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }


    @Test
    void getProjectById() throws Exception {

        Project projectOne = new Project(1L, "test project", 200);

        when(projectService.findById(1L)).thenReturn(Optional.of(projectOne));

        RequestBuilder request = get("/api/v1/project/projects/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName", Matchers.is(projectOne.getProjectName())));


    }
}