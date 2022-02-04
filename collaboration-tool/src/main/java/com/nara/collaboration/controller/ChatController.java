package com.nara.collaboration.controller;

import com.nara.collaboration.dto.ChatMemberDTO;
import com.nara.collaboration.entity.*;
import com.nara.collaboration.service.ChatService;
import com.nara.collaboration.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ProjectService projectService;

    @GetMapping("/project/{email}/{title}/chatting")
    public String chat(Model model, @PathVariable String email, @PathVariable String title,
                       @CurrentUser User user){
        Project project=projectService.getProject(email,title);

        List<ProjectMember> projectMembers=project.getMembers();
        List<ChatMemberDTO> chatmembers=new ArrayList<ChatMemberDTO>();
        for(ProjectMember member:projectMembers){
            User userdata = member.getUser();
            chatmembers.add(new ChatMemberDTO(userdata.getEmail(),
                    userdata.getUsername(),
                    userdata.getIntroduction(),
                    userdata.getProfileImage()));
        }

        List<Chat> chatList=chatService.getChatList(project.getId());
        model.addAttribute(project);
        model.addAttribute("chatList", chatList);
        model.addAttribute("user", user);
        model.addAttribute("members", chatmembers);
        model.addAttribute("projectId", project.getId());
        return "project/chatting";
    }

    @MessageMapping("/chat/{projectId}")
    @SendTo("/receive/chat/{projectId}")
    public Chat chat(@DestinationVariable Long projectId, Chat chat) throws  Exception {

        chat.setSendDateTime(LocalDateTime.now());
        chat.setProjectId(projectId);
        chatService.saveChat(chat);

        return chat;
    }

}
