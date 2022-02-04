package com.nara.collaboration.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProjectBuildForm {

    @Size(min=3,max=20)
    @NotBlank
    private String title;

    private String subTitle;

    private String builderEmail;

}
