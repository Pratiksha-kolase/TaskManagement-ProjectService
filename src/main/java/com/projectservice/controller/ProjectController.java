package com.projectservice.controller;

import com.projectservice.dto.ProjectDto;
import com.projectservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create-project")
    public ResponseEntity<Object> createProject(@RequestBody ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @GetMapping("/get-project-by-id")
    public ResponseEntity<Object> getProjectById(@RequestParam Long projectId) {
        return projectService.getProjectById(projectId);
    }

    @GetMapping("/get-all-projects")
    public ResponseEntity<Object> getAllProjects(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return projectService.getAllProjects(page, pageSize);
    }

    @PatchMapping("/update-project-by-id")
    public ResponseEntity<Object> updateProjectById(@RequestParam Long projectId,@RequestBody ProjectDto projectDto){
        return projectService.updateProjectById(projectId,projectDto);

    }

}
