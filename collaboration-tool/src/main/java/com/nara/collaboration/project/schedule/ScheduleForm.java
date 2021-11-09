package com.nara.collaboration.project.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ScheduleForm {

    private Long id;

    private String date;

    private String title;

    private String content;

    private String color;

}
