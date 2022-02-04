package com.nara.collaboration.validator;

import com.nara.collaboration.dto.ProjectBuildForm;
import com.nara.collaboration.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectBuildValidator implements Validator {

    private final ProjectRepository projectRepository;

    //supprots메소드는 validator가 해당 클래스에 대한 값 검증을 지원하는지에 대한 여부를 리턴한다.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ProjectBuildForm.class);
    }

    //object객체에 대한 검증을 실행하는 메소드,
    // 검증 결과에 대한 문제가 있을 경우에는 errors객체에 여러 정보를 저장
    @Override
    public void validate(Object target, Errors errors) {
        ProjectBuildForm projectBuildForm=(ProjectBuildForm)target;

        if(projectRepository.existsByTitleAndBuilderEmail(projectBuildForm.getTitle(),projectBuildForm.getBuilderEmail())){
            errors.rejectValue("title","invalid.title",
                    new Object[]{projectBuildForm.getTitle()},"이미 사용중인 프로젝트명 입니다.");
        }

    }

}
