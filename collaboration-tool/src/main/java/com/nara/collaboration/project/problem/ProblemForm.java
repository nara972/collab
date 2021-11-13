package com.nara.collaboration.project.problem;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProblemForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
