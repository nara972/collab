package com.nara.collaboration.project.problemshare;


import com.nara.collaboration.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Problem {

    @Id @GeneratedValue
    private Long id;

    private Long projectId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "problem")
    private List<Comment> comments=new ArrayList<Comment>();

}
