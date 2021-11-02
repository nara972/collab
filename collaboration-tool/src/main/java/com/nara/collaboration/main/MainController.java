package com.nara.collaboration.main;

import com.nara.collaboration.user.CurrentUser;
import com.nara.collaboration.user.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {


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
    public String index(@CurrentUser User user, Model model){
        if(user!=null){
            model.addAttribute(user);
        }
        return "index";
    }




}
