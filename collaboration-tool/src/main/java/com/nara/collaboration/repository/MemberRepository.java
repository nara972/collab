package com.nara.collaboration.repository;

import com.nara.collaboration.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<ProjectMember,Long> {

    List<ProjectMember> findAllByProjectId(Long id);

}
