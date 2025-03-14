package com.lab3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab3.entity.Project;
import com.lab3.entity.Task;
import com.lab3.pojo.TaskPojo;
import com.lab3.repository.ProjectRepository;
import com.lab3.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public List<TaskPojo> findAllTask(int id) {
        List<TaskPojo> res = new ArrayList<>();
        List<Task> tasks = taskRepository.findByProjectId(id);
        for (Task task : tasks) {
            res.add(TaskPojo.fromEntity(task));
        }
        return res;
    }

    public TaskPojo findTask(int idProject, int idTask) {
        Task task = taskRepository.findByProjectIdAndId(idProject, idTask);
        if (task == null) {
            return null;
        }
        return TaskPojo.fromEntity(task);
    }

    public TaskPojo createTask(int projectId, TaskPojo taskPojo) {
        Task task = TaskPojo.toEntity(taskPojo);
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            return null;
        }
        task.setProject(project);
        Task taskNew = taskRepository.save(task);
        return TaskPojo.fromEntity(taskNew);
    }

    public TaskPojo updateTask(int projectId, int taskId, TaskPojo taskPojo) {
        Task task = taskRepository.findByProjectIdAndId(projectId, taskId);
        if (task == null) {
            return null;
        }
        task.setName(taskPojo.getName());
        task.setDescription(taskPojo.getDescription());
        task.setEndDate(taskPojo.getEndDate());
        task.setFlag(taskPojo.isFlag());
        return TaskPojo.fromEntity(taskRepository.save(task));
    }

    @Transactional
    public boolean deleteTask(int projectId, int taskId) {
        return taskRepository.deleteByProjectIdAndId(projectId, taskId) > 0;
    }

    @Transactional
    public boolean deleteTaskByFlag(int id) {
        return taskRepository.deleteByProjectIdAndFlagTrue(id) > 0;
    }
}
