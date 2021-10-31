package com.nara.collaboration.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Project {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String subtitle;

    private String builderNick;

    private Long builder;

    private Integer progress=0;

    @Lob
    @Basic(fetch=FetchType.EAGER)
    private String description;

    private LocalDateTime buildDate;
}
