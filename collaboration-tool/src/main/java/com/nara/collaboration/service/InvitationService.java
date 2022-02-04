package com.nara.collaboration.service;

import com.nara.collaboration.entity.Invitation;
import com.nara.collaboration.entity.InvitationType;
import com.nara.collaboration.entity.Project;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository notificationRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public List<Invitation> getSentInviataions(Long projectId) {
        return notificationRepository.findAllByProjectId(projectId);
    }

    public void addNotification(String invitedEmail, String title, String email) {
        User user=userService.getUserByEmail(invitedEmail);
        Project project=projectService.getProject(email, title);
        notificationRepository.save(Invitation.builder()
                .user(user)
                .project(project)
                .createdLocalDateTime(LocalDateTime.now())
                .notificationType(InvitationType.PROJECT_INVITE)
                .message("<b>"+project.getBuilderEmail()+"</b>님의 <b>"+project.getTitle()+"</b>에 초대 받았습니다.")
                .build());
    }

    public List<Invitation> getNotifications(User user) {
        return notificationRepository.findByUserId(user.getId());
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

}
