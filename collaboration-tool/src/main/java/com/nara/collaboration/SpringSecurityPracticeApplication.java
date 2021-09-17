package com.nara.collaboration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityPracticeApplication {
    public static void main(String[] args) {

        System.setProperty("spring.devtools.restart.enabled","false");
        System.setProperty("spring.devtools.livereload.enabled","true");
        SpringApplication.run(SpringSecurityPracticeApplication.class, args);
    }
}
