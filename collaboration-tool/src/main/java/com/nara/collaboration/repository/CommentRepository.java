package com.nara.collaboration.repository;

import com.nara.collaboration.entity.Comment;
import com.nara.collaboration.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    void deleteAllByProblem(Problem problem);

}
