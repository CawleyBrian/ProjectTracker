package com.example.ProjectTracker.Project;

import com.example.ProjectTracker.Exception.ResourceNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/project")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String projectName) {
        List<Project> projects = new ArrayList<Project>();
        if (projectName == null)
            projectService.getProjects().forEach(projects::add);
        else
            projectService.findByprojectNameContaining(projectName).forEach(projects::add);
        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public Project getProjectById(@PathVariable("id") long id) {
        Project project =
                projectService.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Project not found with id + " + id));

        return project;
    }


    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        projectService.addProject(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestParam(required = false) String title,
                                                 @RequestParam(required = false) Integer budget) {
        Project project1 = projectService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id + " + id));

        project1.setProjectName(title);
        project1.setBudget(budget);

        projectService.addProject(project1);
        return new ResponseEntity<>(project1, HttpStatus.OK);
    }

    /* old one
    @PutMapping(path = "{projectId}")
    public void updateProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) Integer budget) {
        projectServi */

    @DeleteMapping(path = "{projectId}")
    public void deleteProject(
            @PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") long id) {
        projectService.deleteProject(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}