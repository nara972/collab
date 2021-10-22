package com.nara.collaboration.user;

import com.nara.collaboration.entity.User;
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

    public User saveUser(@ModelAttribute @Valid SignUpForm signUpForm){
        User user=User.builder()
                .email(signUpForm.getEmail())
                .username(signUpForm.getUsername())
                .password(bCryptPasswordEncoder.encode(signUpForm.getPassword()))
                .role("ROLE_USER")
                .build();
        return userRepository.save(user);
    }


}
