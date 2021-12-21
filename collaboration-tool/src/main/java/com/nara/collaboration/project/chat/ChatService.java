package com.nara.collaboration.project.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    //채팅 저장
    public void saveChat(Chat chat){
        chatRepository.save(chat);
    }
    
    //채팅 목록 가져오기
    public List<Chat> getChatList(Long projectId){
        return chatRepository.findByProjectId(projectId);
    }

}
