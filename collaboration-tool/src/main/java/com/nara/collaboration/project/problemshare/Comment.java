package com.nara.collaboration.project.problemshare;

import com.nara.collaboration.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User writer;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

}
