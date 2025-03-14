package com.lab3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lab3.pojo.TaskPojo;
import com.lab3.service.TaskService;

@RestController
@RequestMapping("/projects")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{projectId}/tasks")
    public List<TaskPojo> findAllTasks(@PathVariable int projectId) {
        return taskService.findAllTask(projectId);
    }
    @GetMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> findTask(@PathVariable int projectId, @PathVariable int taskId) {
        TaskPojo taskPojo = taskService.findTask(projectId, taskId);
        if(taskPojo==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TaskPojo>(taskPojo, HttpStatus.OK);
    }
    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<?> createTask(@PathVariable int projectId, @RequestBody TaskPojo taskPojo)
    {
        TaskPojo task = taskService.createTask(projectId, taskPojo);
        if(task==null)
        {
            return new ResponseEntity<>("Проект с таким id не найден", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }
    @PutMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable int projectId, @PathVariable int taskId, @RequestBody TaskPojo taskPojo)
    {
        TaskPojo res = taskService.updateTask(projectId, taskId, taskPojo);
        if(res!=null)
        {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>("Задача или проект с таким id не найдено", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable int projectId, @PathVariable int taskId)
    {
        if(taskService.deleteTask(projectId, taskId))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Задача или проект с таким id не найдено", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{projectId}/deleteTasks")
    public ResponseEntity<?> deleteTaskByFlag(@PathVariable int projectId)
    {
        if(taskService.deleteTaskByFlag(projectId))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Завершённых задач не найдено", HttpStatus.NOT_FOUND);
    }
}
