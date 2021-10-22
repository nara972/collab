package com.nara.collaboration.user;

import com.nara.collaboration.user.SignUpForm;
import com.nara.collaboration.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        //SignUpForm 타입의 인스턴스를 검사
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SignUpForm signUpForm=(SignUpForm) target;
        if(userRepository.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email","invalid.email",
                    new Object[]{signUpForm.getEmail()},"이미 사용중인 이메일입니다.");
        }
    }
}
