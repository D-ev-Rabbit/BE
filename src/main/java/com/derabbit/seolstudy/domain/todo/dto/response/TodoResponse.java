package com.derabbit.seolstudy.domain.todo.dto.response;

import java.time.LocalDate;

import com.derabbit.seolstudy.domain.todo.Todo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoResponse {

    private Long id;
    private Long menteeId;
    private Long creatorId;
    private String title;
    private LocalDate date;
    private String subject;
    private String goal;
    private Boolean isCompleted;
    private String comment;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .menteeId(todo.getMentee() == null ? null : todo.getMentee().getId())
                .creatorId(todo.getCreator() == null ? null : todo.getCreator().getId())
                .title(todo.getTitle())
                .date(todo.getDate())
                .subject(todo.getSubject())
                .goal(todo.getGoal())
                .isCompleted(todo.getIsCompleted())
                .comment(todo.getComment())
                .build();
    }
}
