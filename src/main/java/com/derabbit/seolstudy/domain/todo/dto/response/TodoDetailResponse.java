package com.derabbit.seolstudy.domain.todo.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.derabbit.seolstudy.domain.todo.Todo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoDetailResponse {

    private Long id;
    private Long userId;
    private Long creatorId;
    private String title;
    private LocalDate date;
    private String subject;
    private String goal;
    private Boolean isCompleted;
    private String comment;
    private List<TodoFileResponse> files;

    public static TodoDetailResponse from(Todo todo, List<TodoFileResponse> files) {
        return TodoDetailResponse.builder()
                .id(todo.getId())
                .userId(todo.getUserId())
                .creatorId(todo.getCreatorId())
                .title(todo.getTitle())
                .date(todo.getDate())
                .subject(todo.getSubject())
                .goal(todo.getGoal())
                .isCompleted(todo.getIsCompleted())
                .comment(todo.getComment())
                .files(files)
                .build();
    }
}
