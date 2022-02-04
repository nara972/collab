package com.nara.collaboration.controller;

import com.nara.collaboration.dto.ScheduleDTO;
import com.nara.collaboration.dto.ScheduleForm;
import com.nara.collaboration.entity.CurrentUser;
import com.nara.collaboration.entity.Project;
import com.nara.collaboration.entity.Schedule;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.service.ProjectService;
import com.nara.collaboration.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project/{email}/{title}")
@RequiredArgsConstructor
public class ScheduleController {

    private final ProjectService projectService;
    private final ScheduleService scheduleService;

    //캘린더
    @GetMapping("/calendar")
    public String calendar(@PathVariable String email, @PathVariable String title, Model model, @CurrentUser User user){

        Project project=projectService.getProject(email,title);

        List<Schedule> schedules=project.getSchedules();
        List<ScheduleDTO> scheduleDTO=new ArrayList<ScheduleDTO>();

        for(Schedule schedule:schedules){
            scheduleDTO.add(new ScheduleDTO(schedule.getId(),
                    schedule.getDate(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getColor()));
        }
        model.addAttribute(project);
        model.addAttribute("title",title);
        model.addAttribute(user);
        model.addAttribute("schedules",scheduleDTO);
        return "project/calendar";
    }

    //스케줄 생성
    @PostMapping("/calendar/make")
    public String makeSchedule(@PathVariable String email, @PathVariable String title,
                               Model model, @CurrentUser User user, @Valid ScheduleForm scheduleForm, Errors errors){

        Project project=projectService.getProject(email,title);
        scheduleService.saveNewSchedule(scheduleForm,project);
        return "redirect:/project/"+email+"/"+project.getTitle()+"/calendar";
    }

    //스케줄 취소
    @PostMapping("/calendar/delete")
    @ResponseBody
    public ResponseEntity deleteSchedule(@RequestBody ScheduleForm scheduleForm){
        Long scheduleId=scheduleForm.getId();
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
    
}
