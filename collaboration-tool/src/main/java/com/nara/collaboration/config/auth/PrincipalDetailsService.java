package com.nara.collaboration.config.auth;

import com.nara.collaboration.entity.User;
import com.nara.collaboration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity =userRepository.findByEmail(email);
        if(userEntity!=null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }

}
