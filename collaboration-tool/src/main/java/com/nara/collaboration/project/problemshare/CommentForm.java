package com.nara.collaboration.project.problemshare;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {

    @NotBlank
    private String content;

    @NotBlank
    private Long problemId;

}
