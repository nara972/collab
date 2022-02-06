package com.nara.collaboration.controller;

import com.nara.collaboration.dto.SignUpForm;
import com.nara.collaboration.entity.CurrentUser;
import com.nara.collaboration.entity.Invitation;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.repository.InvitationRepository;
import com.nara.collaboration.service.InvitationService;
import com.nara.collaboration.service.ProjectService;
import com.nara.collaboration.service.UserService;
import com.nara.collaboration.validator.SignUpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final SignUpValidator signUpValidator;
    private final InvitationService notificationService;
    private final InvitationRepository notificationRepository;
    private final ProjectService projectService;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(Model model){
        model.addAttribute("signUpForm",new SignUpForm());
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid SignUpForm signUpForm, Errors errors){
       if(errors.hasErrors()){
           return "joinForm";
       }
       signUpValidator.validate(signUpForm,errors);
       if(errors.hasErrors()){
           return "joinForm";
       }
        userService.saveUser(signUpForm);
        return "redirect:/loginForm";
    }

    @GetMapping("/profile/{email}")
    public String viewProfile(@PathVariable String email, @CurrentUser User user, Model model){
        User byEmail=userService.getUserByEmail(email);
        List<Invitation> notifications=notificationService.getNotifications(user);

        model.addAttribute("notifications",notifications);
        model.addAttribute(byEmail);

        model.addAttribute("isOwner",byEmail.equals(user));
        model.addAttribute("throughNotification", false);

        return "user/profile";
    }

    @GetMapping("/profile/{email}/notification")
    public String viewNotification(@PathVariable String email,@CurrentUser User user,Model model){
        User byEmail=userService.getUserByEmail(email);
        List<Invitation> notifications=notificationService.getNotifications(user);

        model.addAttribute("notifications",notifications);
        model.addAttribute(byEmail);

        model.addAttribute("isOwner",byEmail.equals(user));
        model.addAttribute("throughNotification", true);

        return "user/profile";
    }
    
    // 초대 수락
    @PostMapping("/profile/{email}/accept/{notificationId}")
    public String acceptInvitation(@PathVariable String email,@PathVariable Long notificationId,@CurrentUser User user){
        Optional<Invitation> notification=notificationRepository.findById(notificationId);
        if(!notification.isPresent()){
            return "redirect:/profile/"+email;
        }
        String projectTitle=notification.get().getProject().getTitle();
        String projectBuilder=notification.get().getProject().getBuilderEmail();

        projectService.saveProjectMember(user.getEmail(),projectTitle,projectBuilder);
        notificationService.deleteNotification(notificationId);

        return String.format("redirect:/project/%s/%s/main",projectBuilder,projectTitle);
    }

    //초대 거절
    @PostMapping("/profile/{email}/reject/{notificationId}")
    public String rejectInvitation(@PathVariable String email,@PathVariable Long notificationId,@CurrentUser User user){
        Optional<Invitation> notification=notificationRepository.findById(notificationId);
        if(!notification.isPresent()){
            return "redirect:/profile/"+email;
        }
        notificationService.deleteNotification(notificationId);
        return "redirect:/profile/"+email;
    }

}
