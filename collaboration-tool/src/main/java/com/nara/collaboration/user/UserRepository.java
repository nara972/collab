package com.nara.collaboration.user;

import com.nara.collaboration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);

    public  User findByEmail(String email);

}
