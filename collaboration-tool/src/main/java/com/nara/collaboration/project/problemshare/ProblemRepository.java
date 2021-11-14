package com.nara.collaboration.project.problemshare;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Long> {

    List<Problem> findByProjectId(Long projectId);

}
