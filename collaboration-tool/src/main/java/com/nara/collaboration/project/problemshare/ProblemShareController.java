package com.nara.collaboration.project.problemshare;

import com.nara.collaboration.project.Project;
import com.nara.collaboration.project.ProjectService;
import com.nara.collaboration.user.CurrentUser;
import com.nara.collaboration.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/project/{email}/{title}")
@RequiredArgsConstructor
public class ProblemShareController {

    private final ProjectService projectService;
    private final ProblemShareService problemShareService;
    
    //문제 공유 페이지
    @GetMapping("/problem-share")
    public String problemShare(@PathVariable String email, @PathVariable String title, Model model,
                               @CurrentUser User user){

        Project project=projectService.getProject(email,title);
        List<Problem> problemList= problemShareService.getProblemList(project.getId());

        model.addAttribute(project);
        model.addAttribute("problemList",problemList);
        model.addAttribute(user);

        return "project/problem-share";
    }
    
    //문제 공유 작성하기
    @PostMapping("/problem-share/post")
    public String problemSharePost(@PathVariable String email,@PathVariable String title,Model model,
                               @CurrentUser User user,@ModelAttribute ProblemForm problemForm){

        Project project=projectService.getProject(email,title);

        problemShareService.saveProblem(problemForm,project.getId(),user);
        return "redirect:/project/"+email+"/"+project.getTitle()+"/problem-share";
    }
    
    //문제 공유 삭제하기
    @PostMapping("/problem-share/{problemId}")
    public String problemShareDelete(@PathVariable String email,@PathVariable String title,@PathVariable Long problemId,
                                     Model model){

        Project project=projectService.getProject(email,title);

        problemShareService.deleteProblem(problemId);
        return "redirect:/project/"+email+"/"+project.getTitle()+"/problem-share";

    }
    
    //문제 공유-코멘트 작성하기
    @PostMapping("/problem-share/comment")
    public  String commentPost(@PathVariable String email,@PathVariable String title,Model model,
                               @CurrentUser User user,CommentForm commentForm){

        Project project=projectService.getProject(email, title);
        problemShareService.saveComment(commentForm,user);

        return "redirect:/project/"+email+"/"+project.getTitle()+"/problem-share";

    }

    //문제 공유-코멘트 삭제하기
    @PostMapping("/problem-share/comment/{commentId}")
    public String commentDelete(@PathVariable String email,@PathVariable String title,Model model,
                                @CurrentUser User user,@PathVariable Long commentId){

        Project project=projectService.getProject(email,title);
        problemShareService.deleteComment(commentId);

        return "redirect:/project/"+email+"/"+project.getTitle()+"/problem-share";
    }


    
    

}