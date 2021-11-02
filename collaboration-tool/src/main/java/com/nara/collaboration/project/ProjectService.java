package com.nara.collaboration.project;

import com.nara.collaboration.user.MemberRepository;
import com.nara.collaboration.user.User;
import com.nara.collaboration.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;

    //프로젝트 저장
    public Project saveNewProject(ProjectBuildForm projectBuildForm, User user){
        Project project=Project.builder()
                .title(projectBuildForm.getTitle())
                .subtitle(projectBuildForm.getSubTitle())
                .builder(user.getId())
                .builderEmail(projectBuildForm.getBuilderEmail())
                .buildDate(LocalDateTime.now())
                .build();
        projectRepository.save(project);
        saveProjectMember(user.getEmail(),project.getTitle(),project.getBuilderEmail());
        return project;
    }

    public void saveProjectMember(String email,String title,String builderEmail){
        User user=userService.getUserByEmail(email);
        Project project=projectRepository.findByTitleAndBuilderEmail(title,builderEmail);

        memberRepository.save(ProjectMember.builder()
                .user(user)
                .project(project)
                .joinDate(LocalDateTime.now())
                .build());
    }

}
