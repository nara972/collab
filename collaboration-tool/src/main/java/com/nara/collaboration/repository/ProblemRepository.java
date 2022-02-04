package com.nara.collaboration.repository;

import com.nara.collaboration.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Long> {

    List<Problem> findByProjectId(Long projectId);

}
