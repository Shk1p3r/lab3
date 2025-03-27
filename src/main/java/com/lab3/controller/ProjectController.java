package com.lab3.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lab3.pojo.ProjectPojo;
import com.lab3.service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @GetMapping("/{projectId}")
    public ResponseEntity<?> findProject(@PathVariable int projectId)
    {
        ProjectPojo projectPojo = projectService.findProject(projectId);
        if(projectPojo==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectPojo, HttpStatus. OK);
    }
    @GetMapping()
    public ResponseEntity<?> getMethodName(@RequestParam(name = "search", required = false) String search) 
    {
        return new ResponseEntity<>(projectService.findBySearch(search), HttpStatus.OK);
    }
    @PostMapping("/change/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectPojo projectPojo) 
    {
        return new ResponseEntity<>(projectService.createProject(projectPojo), HttpStatus.OK);
    }
    @PutMapping("/change/update/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable int projectId, @RequestBody ProjectPojo projectPojo)    
    {
        ProjectPojo res = projectService.updateProject(projectId, projectPojo);
        if(res!=null)
        {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>("Задача или проект с таким id не найдено", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/change/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable int projectId)    
    {
        if(projectService.deleteProject(projectId))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Проект с таким id не найден", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/change/flag")
    public Map<Integer, Long> countProject()
    {
        return projectService.countProject();
    }
}
