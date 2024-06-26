package ru.otus.hw.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String text;

}