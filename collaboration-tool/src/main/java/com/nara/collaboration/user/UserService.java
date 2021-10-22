package com.nara.collaboration.user;

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

    public User getUserByEmail(String email){
        User user=userRepository.findByEmail(email);
        return user;
    }



}
