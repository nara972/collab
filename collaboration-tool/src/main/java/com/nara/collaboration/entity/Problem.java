package com.nara.collaboration.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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

    //문제 공유 삭제 가능 확인
    public boolean isDeleteable(User user){
        if(this.writer.getId()!=user.getId()){
            throw new IllegalArgumentException("허용되지 않은 유저입니다.");
        }
        return this.writer.equals(user);
    }

}
