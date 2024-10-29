package com.projectservice.serviceimpl;

import com.projectservice.dto.ProjectDto;
import com.projectservice.model.ProjectModel;
import com.projectservice.repository.ProjectRepository;
import com.projectservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ResponseEntity<Object> createProject(ProjectDto projectDto) {
        try {
            Optional<ProjectModel> project = projectRepository.findByProjectName(projectDto.getProjectName());
            if (project.isPresent()) {
                return new ResponseEntity<>("Project Already Exists", HttpStatus.OK);
            }
            ProjectModel projectModel = new ProjectModel();
            projectModel.setProjectDescription(projectDto.getProjectDescription());
            projectModel.setProjectName(projectDto.getProjectName());
            projectModel.setCreatedDate(LocalDateTime.now());
            projectRepository.save(projectModel);
            return new ResponseEntity<>("Project Created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error during creation of project: " + e, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getProjectById(Long projectId) {
        try {
            Optional<ProjectModel> project = projectRepository.findById(projectId);
            return project.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok("Not found"));
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't fetch project details: " + e, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getAllProjects(Integer page, Integer pageSize) {
        try {
            Page<ProjectModel> projects = projectRepository.findAll(PageRequest.of(page, pageSize));
            if (projects.hasContent()) {
                Map<String, Object> map = new HashMap<>();
                map.put("project_data", projects.getContent());
                map.put("totalPages", projects.getTotalPages());
                map.put("pageSize", projects.getTotalElements());
                return ResponseEntity.ok(map);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @Override
    public ResponseEntity<Object> updateProjectById(Long projectId, ProjectDto projectDto) {
        try {
            Optional<ProjectModel> project = projectRepository.findById(projectId);
            if (project.isPresent()) {
                ProjectModel projectModel = new ProjectModel();
                if (!projectDto.getProjectName().isEmpty()) {
                    projectModel.setProjectName(projectDto.getProjectName());
                }
                if (!projectDto.getProjectDescription().isEmpty()) {
                    projectModel.setProjectDescription(projectDto.getProjectDescription());
                }
                projectModel.setUpdatedDate(LocalDateTime.now());
                ProjectModel updatedProject = projectRepository.save(projectModel);
                return new ResponseEntity<>(updatedProject, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
