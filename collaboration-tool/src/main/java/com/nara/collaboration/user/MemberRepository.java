package com.nara.collaboration.user;

import com.nara.collaboration.project.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<ProjectMember,Long> {
}
