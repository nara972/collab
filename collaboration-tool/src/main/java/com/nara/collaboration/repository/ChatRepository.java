package com.nara.collaboration.repository;

import com.nara.collaboration.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    List<Chat> findByProjectId(Long projectId);

}
