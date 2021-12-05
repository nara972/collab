package com.nara.collaboration.user;

import com.nara.collaboration.config.auth.PrincipalDetails;
import com.nara.collaboration.notification.Notification;
import com.nara.collaboration.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final SignUpValidator signUpValidator;
    private final NotificationService notificationService;

    @GetMapping("/test/login")
    public @ResponseBody
    String testLogin(Authentication authentication, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("/test/login===============");
        PrincipalDetails principalDetails=(PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication:"+principalDetails.getUser());
        System.out.println("userDetails:"+userDetails.getUsername());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,@AuthenticationPrincipal OAuth2User oAuth2User){
        System.out.println("/test//oauth/login===============");
        OAuth2User auth2User=(OAuth2User) authentication.getPrincipal();
        System.out.println("authentication:"+auth2User.getAttributes());
        System.out.println("oAuth2User:"+oAuth2User.getAttributes());
        return "OAuth 세션 정보 확인하기";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails:"+principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

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
    public String viewProfile(@PathVariable String email,@CurrentUser User user,Model model){
        User byEmail=userService.getUserByEmail(email);
        List<Notification> notifications=notificationService.getNotifications(user);

        model.addAttribute("notifications",notifications);
        model.addAttribute(byEmail);

        model.addAttribute("isOwner",byEmail.equals(user));
        model.addAttribute("throughNotification", false);

        return "user/profile";
    }

    @GetMapping("/profile/{email}/notification")
    public String viewNotification(@PathVariable String email,@CurrentUser User user,Model model){
        User byEmail=userService.getUserByEmail(email);
        List<Notification> notifications=notificationService.getNotifications(user);

        model.addAttribute("notifications",notifications);
        model.addAttribute(byEmail);

        model.addAttribute("isOwner",byEmail.equals(user));
        model.addAttribute("throughNotification", true);

        return "user/profile";
    }

}
