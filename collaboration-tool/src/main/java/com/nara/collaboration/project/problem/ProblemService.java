package com.nara.collaboration.project.problem;

import com.nara.collaboration.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {

    private final ProblemRepository problemRepository;

    //문제 공유 글 리스트
    public List<Problem> getProblem(Long projectId){
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

}
