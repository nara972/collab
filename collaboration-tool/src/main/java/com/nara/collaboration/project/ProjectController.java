package com.nara.collaboration.project;

import com.nara.collaboration.user.CurrentUser;
import com.nara.collaboration.user.User;
import com.nara.collaboration.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectBuildValidator projectBuildValidator;
    
    //projectBuildForm이라는 특정 모델 객체에만 바인딩 또는 검증 설정을 변경
    @InitBinder("projectBuildForm")
    public void projectInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(projectBuildValidator);
    }
    
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
    public String buildProject(@Valid @ModelAttribute ProjectBuildForm projectBuildForm,Errors errors,
                               @CurrentUser User user, Model model){

        if(errors.hasErrors()){
            User byEmail=userService.getUserByEmail(user.getEmail());
            List<ProjectMember> projectList=byEmail.getProjects();
            model.addAttribute("projectList",projectList);
            model.addAttribute(user);
            model.addAttribute("error", "이미 사용중인 프로젝트명 입니다.");
            return "main";
        }

        projectService.saveNewProject(projectBuildForm,user);
        return "redirect:/main";

    }

}
