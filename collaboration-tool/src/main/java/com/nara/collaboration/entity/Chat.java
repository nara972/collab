package com.nara.collaboration.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Chat {

    @Id @GeneratedValue
    private Long id;

    private Long projectId;

    private String sender;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String message;

    @CreatedDate
    private LocalDateTime sendDateTime;
}
