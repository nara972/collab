package com.nara.collaboration.dto;

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
