package com.redditApp.post_service.dtos;


import com.redditApp.post_service.enums.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreatePostRequest {

    @NotNull
    private Long subRedditId;

    @NotBlank
    @Size(max = 300)
    private String title;

    @NotNull
    private PostType type;

    private String content;
}
