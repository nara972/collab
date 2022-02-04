package com.nara.collaboration.repository;

import com.nara.collaboration.entity.Invitation;
import com.nara.collaboration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface InvitationRepository extends JpaRepository<Invitation,Long> {

    List<Invitation> findAllByProjectId(Long projectId);

    long countByUser(User user);

    List<Invitation> findByUserId(Long userId);
}
