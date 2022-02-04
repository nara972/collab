package com.nara.collaboration.repository;

import com.nara.collaboration.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long>{

    Project findByTitleAndBuilderEmail(String title,String builderEmail);

    boolean existsByTitleAndBuilderEmail(String title, String builderEmail);
}
