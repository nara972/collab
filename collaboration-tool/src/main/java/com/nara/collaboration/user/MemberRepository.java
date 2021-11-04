package com.nara.collaboration.user;

import com.nara.collaboration.project.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<ProjectMember,Long> {

    List<ProjectMember> findAllByProjectId(Long id);

}
