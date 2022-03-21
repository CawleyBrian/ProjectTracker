package com.example.ProjectTracker.Task;

import com.example.ProjectTracker.Exception.ResourceNotFoundException;
import com.example.ProjectTracker.Project.Project;
import com.example.ProjectTracker.Project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    private final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    private ProjectService projectService;


    @GetMapping()
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    //Return tasks by Project Id.
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<Task>> getAllTasksByProjectId(@PathVariable(value = "projectId") Long projectId) {
        if (projectService.findById(projectId).isPresent()) {
            List<Task> tasks = taskService.findByProjectId(projectId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No tasks found for project with id = " + projectId);
        }
    }

    @PostMapping
    public void addTask(@RequestBody Task task) {
        taskService.addTask(task);
    }

    @PostMapping("/projects/{projectId}/task")
    public ResponseEntity<Task> createComment(@PathVariable(value = "projectId") Long projectId,
                                              @RequestBody Task taskRequest) {
        Task comment = projectService.findById(projectId).map(project -> {
            taskRequest.setProject(project);
            return taskService.saveTask(taskRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("No project with id = " + projectId));
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }




    //Delete single task by Id
    @DeleteMapping(path = "{taskId}")
    public void deleteTask(
            @PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }

    @DeleteMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<Task>> deleteAllTasksOfProject(@PathVariable(value = "projectId") Long projectId) {
        if (projectService.findById(projectId) == null) {
            throw new ResourceNotFoundException("No project with id = " + projectId);
        }
        taskService.deleteByProjectId(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping(path = "{taskId}")
    public void updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description) {
        taskService.updateTask(taskId, title, description);
    }

    //Find tasks under existing ProjectId and Move to new ProjectID
    @PutMapping(path = "/task/{projectId}/project")
    public ResponseEntity<List<Task>> moveTasksByProjectId(
            @PathVariable(value="projectId") Long projectId,
            @RequestParam(required = true) Long previousId){
        Project project = projectService.getProject(projectId);
        if(project == null){
            throw new ResourceNotFoundException("New project not found");
        }
        if(projectService.getProject(previousId) == null){
            throw new ResourceNotFoundException("Existing project not found");
        }

        taskService.moveTasksByProject(project, previousId);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}