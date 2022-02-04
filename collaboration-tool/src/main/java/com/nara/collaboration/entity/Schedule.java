package com.nara.collaboration.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Schedule {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    private String date;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private String color;

}
