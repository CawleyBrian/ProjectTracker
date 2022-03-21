package com.example.ProjectTracker.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findByProjectId(Long projectId);

    @Transactional
    void deleteByProjectId(Long projectId);

}