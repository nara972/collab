package com.nara.collaboration.config.oauth;

import com.nara.collaboration.config.auth.PrincipalDetails;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration());//registrationId로 어떤 OAuth로 로그인 했는지 확인
        System.out.println("getAccessToken:"+userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User=super.loadUser(userRequest);
        //구글로그인 버튼 클릭->구글로그인창->로그인을 완료->code리턴(OAuth-Client라이브러리)->AccessToken요청
        //userRequest 정보->loadUser함수 호출->구글로부터 회원프로필 받아줌
        System.out.println("getAttributes:"+oAuth2User.getAttributes());

        String email=oAuth2User.getAttribute("email");

        Optional<User> userEntity=userRepository.findByEmail(email);

        User user=userEntity.orElseGet(()->registerNewUser(userRequest));

        return new PrincipalDetails(user,oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest userRequest){

        OAuth2User oAuth2User=super.loadUser(userRequest);

        //회원가입을 강제로 진행
        String provider=userRequest.getClientRegistration().getClientId(); //google
        String providerId=oAuth2User.getAttribute("sub");
        //String username=provider+"_"+providerId;//google_10808979874425
        String username=oAuth2User.getAttribute("name");
        String password=bCryptPasswordEncoder.encode("겟인데어");
        String email=oAuth2User.getAttribute("email");
        String role="ROLE_USER";

        User user=User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .provider(provider)
                .providerId(providerId)
                .build();

        return userRepository.save(user);

    }

}