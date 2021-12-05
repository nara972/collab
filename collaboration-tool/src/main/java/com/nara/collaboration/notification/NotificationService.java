package com.nara.collaboration.notification;

import com.nara.collaboration.project.Project;
import com.nara.collaboration.project.ProjectService;
import com.nara.collaboration.user.User;
import com.nara.collaboration.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public List<Notification> getSentInviataions(Long projectId) {
        return notificationRepository.findAllByProjectId(projectId);
    }

    public void addNotification(String invitedEmail, String title, String email) {
        User user=userService.getUserByEmail(invitedEmail);
        Project project=projectService.getProject(email, title);
        notificationRepository.save(Notification.builder()
                .user(user)
                .project(project)
                .createdLocalDateTime(LocalDateTime.now())
                .notificationType(NotificationType.PROJECT_INVITE)
                .message("<b>"+project.getBuilderEmail()+"</b>님의 <b>"+project.getTitle()+"</b>에 초대 받았습니다.")
                .build());
    }

    public List<Notification> getNotifications(User user) {
        return notificationRepository.findByUserId(user.getId());
    }
}
