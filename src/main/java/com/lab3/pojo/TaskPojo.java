package com.lab3.pojo;

import java.sql.Date;

import com.lab3.entity.Task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskPojo {
    private int id;
    private String name;
    private String description;
    private Date endDate;
    private boolean flag;
    public static TaskPojo fromEntity(Task task)
    {
        if (task == null) {
            return null;
        }
        TaskPojo pojo = new TaskPojo();
        pojo.setId(task.getId());
        pojo.setName(task.getName());
        pojo.setDescription(task.getDescription());
        pojo.setFlag(task.isFlag());
        pojo.setEndDate(task.getEndDate());
        return pojo;
    }
    public static Task toEntity(TaskPojo pojo)
    {
        Task task = new Task();
        task.setId(pojo.getId());
        task.setName(pojo.getName());
        task.setDescription(pojo.getDescription());
        task.setFlag(pojo.isFlag());
        task.setEndDate(pojo.getEndDate());
        return task;
    }
}
