package com.nara.collaboration.notification;

import com.nara.collaboration.project.Project;
import com.nara.collaboration.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Builder
@Data @EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id @GeneratedValue
    private Long id;

    private String message;

    private boolean checked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDateTime createdLocalDateTime;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


}
