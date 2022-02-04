package com.nara.collaboration.controller;

import com.nara.collaboration.entity.CurrentUser;
import com.nara.collaboration.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(@CurrentUser User user, Model model){
        if(user!=null){
            model.addAttribute(user);
        }
        return "index";
    }

}
