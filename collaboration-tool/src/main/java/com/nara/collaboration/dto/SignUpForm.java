package com.nara.collaboration.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {

    @NotBlank
    @Size(min=3,max=20)
    @Pattern(regexp="^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min=6,max = 50)
    private String password;

}
