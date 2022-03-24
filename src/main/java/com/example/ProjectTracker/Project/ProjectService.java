//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.ProjectTracker.Project;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;

import com.example.ProjectTracker.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return this.projectRepository.findAll();
    }

    public Optional<Project> findById(long id) {
        return this.projectRepository.findById(id);
    }

    public List<Project> findByProjectNameContaining(String projectName) {
        return this.projectRepository.findByProjectNameContaining(projectName);
    }

    public Project addProject(Project project) {
        return this.projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        if (!this.projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " does not exist");
        } else {
            this.projectRepository.deleteById(projectId);
        }
    }

    @Transactional
    public void updateProject(Long projectId, String projectName, Integer budget) {
        Project project = (Project) this.projectRepository.findById(projectId).orElseThrow(() -> {
            return new ResourceNotFoundException("project " + projectId + "not found");
        });
        if (projectName != null && projectName.length() > 0 && !Objects.equals(project.getProjectName(), projectName)) {
            project.setProjectName(projectName);
        }
        if (budget != null && budget > 0) {
            project.setBudget(budget);
        }

        System.out.println(project);
    }


}
