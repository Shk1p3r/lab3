package com.lab3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lab3.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{
    List<Task> findByProjectId(int idProject);
    Task findByProjectIdAndId(int idProject, int idTask);
    int deleteByProjectIdAndId(int idProject, int idTask);
    int deleteByProjectIdAndFlagTrue(int idProject);
}
