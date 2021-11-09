package com.nara.collaboration.project.schedule;

import com.nara.collaboration.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    
    //스케줄 저장
    public Schedule saveNewSchedule(ScheduleForm scheduleForm, Project project){
        Schedule schedule=Schedule.builder()
                .project(project)
                .date(scheduleForm.getDate())
                .title(scheduleForm.getTitle())
                .content(scheduleForm.getContent())
                .color(scheduleForm.getColor())
                .build();
        project.addSchedule(schedule);
        return scheduleRepository.save(schedule);
    }
}
