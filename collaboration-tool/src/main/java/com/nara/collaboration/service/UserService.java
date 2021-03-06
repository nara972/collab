package com.nara.collaboration.service;

import com.nara.collaboration.dto.SignUpForm;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.exception.ResourceNotFoundException;
import com.nara.collaboration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입
    public User saveUser(@ModelAttribute @Valid SignUpForm signUpForm){
        User user=User.builder()
                .email(signUpForm.getEmail())
                .username(signUpForm.getUsername())
                .password(bCryptPasswordEncoder.encode(signUpForm.getPassword()))
                .role("ROLE_USER")
                .build();
        return userRepository.save(user);
    }
    
    //로그인시 이메일로 유저 검사
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User", "email", email));
    }
}
