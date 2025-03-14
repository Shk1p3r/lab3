package com.lab3.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab3.entity.Project;
import com.lab3.entity.Task;
import com.lab3.pojo.ProjectPojo;
import com.lab3.pojo.TaskPojo;
import com.lab3.repository.ProjectRepository;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    public ProjectPojo findProject(int projectId)
    {
        Project project = projectRepository.findById(projectId);
        return ProjectPojo.fromEntity(project);
    }
    public List<ProjectPojo> findBySearch(String search)
    {
        List<ProjectPojo> projectPojos = new ArrayList<>();
        List<Project> projects = search == null ? projectRepository.findAll() : projectRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
        for(Project value : projects)
        {
            projectPojos.add(ProjectPojo.fromEntity(value));
        }
        return projectPojos;
    }
    public ProjectPojo createProject(ProjectPojo projectPojo)
    {
        Project project = ProjectPojo.toEntity(projectPojo);
        Project newProject = projectRepository.save(project);
        return ProjectPojo.fromEntity(newProject);
    }
    public ProjectPojo updateProject(int projectId, ProjectPojo projectPojo)
    {
        Project project = projectRepository.findById(projectId);
        if(project==null)
        {
            return null;
        }
        project.setName(projectPojo.getName());
        project.setDescription(projectPojo.getDescription());
        project.setEndDate(projectPojo.getEndDate());
        project.setStartDate(projectPojo.getStartDate());
        List<TaskPojo> tasksPojo = projectPojo.getTasks();
        if(tasksPojo!=null)
        {
            project.getTasks().clear();
            for(TaskPojo pojo : tasksPojo)
            {
                Task task = TaskPojo.toEntity(pojo);
                task.setProject(project);
                project.getTasks().add(task);
            }
        }
        return  ProjectPojo.fromEntity(projectRepository.save(project));
    }
    public boolean deleteProject(int projectId)
    {
        Project project = projectRepository.findById(projectId);
        if(project==null)
        {
            return false;
        }
        projectRepository.delete(project);
        return true;
    }
    public Map<Integer, Long> countProject()
    {
        Map<Integer,Long> res = new HashMap<>();
        var projects = projectRepository.countProjectWithTasksFlagFalse();
        for(var project : projects)
        {
            res.put(project.get("id").intValue(), project.get("count").longValue());
        }
        
        return res;
    }
}
