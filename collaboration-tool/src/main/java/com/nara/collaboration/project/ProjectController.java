package com.nara.collaboration.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nara.collaboration.notification.Notification;
import com.nara.collaboration.notification.NotificationService;
import com.nara.collaboration.user.CurrentUser;
import com.nara.collaboration.user.User;
import com.nara.collaboration.user.UserRepository;
import com.nara.collaboration.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectBuildValidator projectBuildValidator;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    
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
    
    //프로젝트별 메인 화면
    @GetMapping("/project/{email}/{title}/main")
    public String projectMain(@PathVariable String email,@PathVariable String title,Model model, @CurrentUser User user){
        Project project=projectService.getProject(email,title);
        List<ProjectMember> members=projectService.getMemberList(project);

        //프로젝트에 필요한 정보
        //유저가 해당 프로젝트의 멤버인지 확인
        model.addAttribute(project);
        model.addAttribute(members);
        model.addAttribute(user);
        return "project/project-main";
    }

    //프로젝트 설명 작성 페이지
    @GetMapping("/project/{email}/{title}/main/description")
    public String projectDescription(@PathVariable String email,@PathVariable String title,Model model, @CurrentUser User user){

        Project project=projectService.getProject(email,title);
        model.addAttribute(project);
        model.addAttribute(user);

        return "project/description";
    }


    //프로젝트 설명 추가 & 수정
    @PostMapping("/project/{email}/{title}/main/description")
    public String projectDescription(@PathVariable String email,@PathVariable String title,Model model,
                                     @CurrentUser User user,String description){

        Project project=projectService.getProject(email,title);
        projectService.saveDescription(project,description);

        return "redirect:/project/"+email+"/"+project.getTitle()+"/main";

    }

    //프로젝트 설정 페이지
    @GetMapping("/project/{email}/{title}/settings")
    public String settings(@PathVariable String email,@PathVariable String title,Model model,
                           @CurrentUser User user) throws JsonProcessingException {
        Project project=projectService.getProject(email,title);
        List<ProjectMember> members = projectService.getMemberList(project);

        model.addAttribute(user);
        model.addAttribute("members",members);
        model.addAttribute(project);

        List<String> userList=userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
        model.addAttribute("whitelist",objectMapper.writeValueAsString(userList));

        List<Notification> sentInvitations=notificationService.getSentInviataions(project.getId());
        model.addAttribute("sentInvitations",sentInvitations.stream()
                .map(Notification::getUser)
                .map(User::getEmail).collect(Collectors.toList()));

        return "project/setting";
    }

    @PostMapping("/project/{email}/{title}/settings/add")
    @ResponseBody
    public ResponseEntity addMember(@RequestBody @Valid MemberForm memberForm,
                                    Errors errors, @PathVariable("title") String title,
                                    @PathVariable("email") String email, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("error", "존재하지 않는 이메일입니다.");
            return ResponseEntity.badRequest().build();
        }
        String memberEmail = memberForm.getMemberEmail();

        log.info("title: " + title);

        notificationService.addNotification(memberEmail, title, email);
        return ResponseEntity.ok().build();
    }

























}
