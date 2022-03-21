//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.ProjectTracker.Project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "projects"
)
public class Project {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "project_generator"
    )
    private Long id;
    @Column(
            name = "project_name"
    )
    private String projectName;
    @Column(
            name = "budget"
    )
    private Integer budget;

    public Project() {
    }

    public Project(String projectName, Integer budget) {
        this.projectName = projectName;
        this.budget = budget;
    }

    public Project(Long id, String projectName, Integer budget) {
        this.id = id;
        this.projectName = projectName;
        this.budget = budget;
    }

    public String toString() {
        return "Project{id=" + this.id + ", projectName='" + this.projectName + "', budget='" + this.budget + "'}";
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getBudget() {
        return this.budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }
}
