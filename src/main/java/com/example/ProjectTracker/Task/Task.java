package com.example.ProjectTracker.Task;

import com.example.ProjectTracker.Project.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="type")
    private String type;
    @Column(name="estimated_hours")
    private Integer estimatedHours;
    @Column(name="actual_hours")
    private Integer actualHours;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Project project;

    protected Task() {
    }

    public Task(Long id, String title, String description, Project project, String type, Integer estimatedHours, Integer actualHours) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.project = project;
        this.type = type;
        this.estimatedHours = estimatedHours;
        this.actualHours = actualHours;
    }

    public Task(String title, String description, Project project, String type, Integer estimatedHours, Integer actualHours) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.type = type;
        this.estimatedHours=estimatedHours;
        this.actualHours=actualHours;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getActualHours() {
        return actualHours;
    }

    public void setActualHours(Integer actualHours) {
        this.actualHours = actualHours;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", project='" + project + '\'' +
                ", type='" + type + '\'' +
                ", estimatedHours=" + estimatedHours +
                ", actualHours=" + actualHours +
                '}';
    }
}