package com.nara.collaboration.notification;

import com.nara.collaboration.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByProjectId(Long projectId);

    long countByUser(User user);

    List<Notification> findByUserId(Long userId);
}
