package com.example.ProjectTracker.Task;

import com.example.ProjectTracker.Exception.ResourceNotFoundException;
import com.example.ProjectTracker.Project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }


    public List<Task> findByProjectId(Long projectId){
        if (!taskRepository.findByProjectId(projectId).isEmpty()){
            return taskRepository.findByProjectId(projectId);
        } else throw new ResourceNotFoundException("No tasks for project Id " + projectId);
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else throw new IllegalStateException(
                "task " + taskId + " does not exist");
    }

    public void deleteByProjectId(Long projectId){
        taskRepository.deleteByProjectId(projectId);
    }

    @Transactional
    public void updateTask(Long taskId,
                           String title,
                           String description) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalStateException(
                        "task " + taskId + "not found"));

        if (title != null &&
                title.length() > 0 &&
                !Objects.equals(task.getTitle(), title)) {
            task.setTitle(title);
        }

        if (description != null &&
                description.length()>0){
            task.setDescription(description);
        }

        System.out.println(task);

    }


    public void moveTasksByProject(Project project, Long previousId){


        List<Task> tasks = findByProjectId(previousId);
        if(tasks.isEmpty()){
            throw new ResourceNotFoundException("No tasks found for project id " + project.getId());
        }

        tasks.stream().forEach(task -> task.setProject(project));
        taskRepository.saveAll(tasks);
    }


    public Task saveTask(Task taskRequest) {
        return taskRepository.save(taskRequest);
    }
}