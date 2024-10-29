package com.projectservice.repository;

import com.projectservice.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel,Long> {
    Optional<ProjectModel> findByProjectName(String projectName);
}
