package com.nara.collaboration.service;

import com.nara.collaboration.dto.CommentForm;
import com.nara.collaboration.dto.ProblemForm;
import com.nara.collaboration.entity.Comment;
import com.nara.collaboration.entity.Problem;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.repository.CommentRepository;
import com.nara.collaboration.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemShareService {

    private final ProblemRepository problemRepository;
    private final CommentRepository commentRepository;

    //문제 공유 글 리스트
    public List<Problem> getProblemList(Long projectId){
        return problemRepository.findByProjectId(projectId);
    }

    //문제 공유 글 작성하기
    public Problem saveProblem(ProblemForm problemForm, Long projectId, User user) {

        Problem problem=Problem.builder()
                .title(problemForm.getTitle())
                .content(problemForm.getContent())
                .projectId(projectId)
                .writer(user)
                .build();
        return problemRepository.save(problem);

    }

    //문제 공유 글 삭제하기
    public void deleteProblem(Long problemId) {
        problemRepository.deleteById(problemId);
    }
    
    //하나의 문제 공유 글 얻어오기
    public Problem getProblemOne(Long problemId){
        return problemRepository.findById(problemId).get();
    }

    //문제 공유-코멘트 작성하기
    public Comment saveComment(CommentForm commentForm, User user) {

        Problem problem=getProblemOne(commentForm.getProblemId());
        Comment comment=Comment.builder()
                .problem(problem)
                .content(commentForm.getContent())
                .writer(user)
                .build();
        return commentRepository.save(comment);

    }

    //문제 공유-코멘트 삭제하기
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
