package com.lab3.pojo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import com.lab3.entity.Task;
import com.lab3.entity.Project;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectPojo {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<TaskPojo> tasks = new ArrayList<>();
    public static ProjectPojo fromEntity(Project project)
    {
        if(project==null)
        {
            return null;
        }
        ProjectPojo pojo = new ProjectPojo();
        pojo.setId(project.getId());
        pojo.setName(project.getName());
        pojo.setDescription(project.getDescription());
        pojo.setStartDate(project.getStartDate());
        pojo.setEndDate(project.getEndDate());
        List<TaskPojo> tasks = new ArrayList<>();
        for(Task task : project.getTasks())
        {
            tasks.add(TaskPojo.fromEntity(task));
        }
        pojo.setTasks(tasks);
        return pojo;
    }
    public static Project toEntity(ProjectPojo pojo) {
        if (pojo == null) {
            return null;
        }
        Project project = new Project();
        project.setId(pojo.getId());
        project.setName(pojo.getName());
        project.setDescription(pojo.getDescription());
        project.setStartDate(pojo.getStartDate());
        project.setEndDate(pojo.getEndDate());
        List<Task> tasks = new ArrayList<>();
        if (pojo.getTasks() != null) {
            for (TaskPojo taskPojo : pojo.getTasks()) {
                tasks.add(TaskPojo.toEntity(taskPojo));
            }
        }
        project.setTasks(tasks);
        return project;
    }
}
