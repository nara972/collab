package com.nara.collaboration.repository;

import com.nara.collaboration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public  User findByEmail(String email);

}
