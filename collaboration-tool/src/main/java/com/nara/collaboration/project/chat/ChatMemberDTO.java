package com.nara.collaboration.project.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ChatMemberDTO {

    private String email;

    private String username;

    private String introduction;

    private String profileImage;

}
