package com.nara.collaboration.project;

import com.nara.collaboration.user.CurrentUser;
import com.nara.collaboration.user.User;
import com.nara.collaboration.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    
    //프로젝트-메인 화면
    @GetMapping("/main")
    public String main(@CurrentUser User user, Model model){
        User byEmail=userService.getUserByEmail(user.getEmail());
        List<ProjectMember> projectList=byEmail.getProjects();
        model.addAttribute("projectList",projectList);
        model.addAttribute(user);
        return "main";
    }

    //프로젝트 생성
    @PostMapping("/main")
    public String buildProject(@Valid @ModelAttribute ProjectBuildForm projectBuildForm,
                               @CurrentUser User user, Model model){

        projectService.saveNewProject(projectBuildForm,user);
        return "redirect:/main";

    }
}
