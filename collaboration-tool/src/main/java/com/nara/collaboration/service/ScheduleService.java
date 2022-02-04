package com.nara.collaboration.service;

import com.nara.collaboration.dto.ScheduleForm;
import com.nara.collaboration.entity.Project;
import com.nara.collaboration.entity.Schedule;
import com.nara.collaboration.repository.ScheduleRepository;
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

    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
