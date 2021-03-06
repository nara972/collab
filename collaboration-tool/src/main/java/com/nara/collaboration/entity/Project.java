package com.nara.collaboration.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Data @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Project {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String subtitle;

    private String builderEmail;

    private Long builder;

    private Integer progress=0;

    @Lob
    @Basic(fetch=FetchType.EAGER)
    private String description;

    private LocalDateTime buildDate;

    @OneToMany(mappedBy = "project")
    private List<ProjectMember> members=new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Schedule> schedules=new ArrayList<>();

    public void addSchedule (Schedule schedule) {
        this.schedules.add(schedule);
    }

    public String getEncodedTitle(){
        return URLEncoder.encode(this.title, StandardCharsets.UTF_8);
    }

    public void parseTitle() {
        this.title=this.title.trim().replace(" ","-");
    }

}
