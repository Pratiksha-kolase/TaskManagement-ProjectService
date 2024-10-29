package com.projectservice.service;

import com.projectservice.dto.ProjectDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface ProjectService {

    ResponseEntity<Object> createProject(ProjectDto projectDto);

    ResponseEntity<Object> getProjectById(Long projectId);

    ResponseEntity<Object> getAllProjects(Integer page,Integer pageSize);

    ResponseEntity<Object> updateProjectById(Long projectId, ProjectDto projectDto);
}
