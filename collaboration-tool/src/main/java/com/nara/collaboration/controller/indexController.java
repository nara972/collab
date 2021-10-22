package com.nara.collaboration.controller;

import com.nara.collaboration.config.auth.PrincipalDetails;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.user.UserRepository;
import com.nara.collaboration.user.SignUpForm;
import com.nara.collaboration.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class indexController {


    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보???";
    }



    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user,Model model){
        if(user!=null){
            model.addAttribute(user);
        }
        return "index";
    }


}
