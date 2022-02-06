package com.nara.collaboration.service;

import com.nara.collaboration.dto.ProjectBuildForm;
import com.nara.collaboration.entity.Project;
import com.nara.collaboration.entity.ProjectMember;
import com.nara.collaboration.entity.User;
import com.nara.collaboration.repository.MemberRepository;
import com.nara.collaboration.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
        project.parseTitle();
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

    public Project getProject(String email, String title) {
        Project project=projectRepository.findByTitleAndBuilderEmail(title,email);
        return project;
    }

    public List<ProjectMember> getMemberList(Project project) {
        return memberRepository.findAllByProjectId(project.getId());
    }

    public void saveDescription(Project project, String description) {
        project.setDescription(description);
        projectRepository.save(project);
    }

}
