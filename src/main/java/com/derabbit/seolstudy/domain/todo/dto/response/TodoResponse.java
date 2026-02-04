package com.derabbit.seolstudy.domain.todo.dto.response;

import java.time.LocalDate;

import com.derabbit.seolstudy.domain.todo.Todo;
import com.derabbit.seolstudy.domain.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoResponse {

    private Long id;
    private User mentee;
    private User creator;
    private String title;
    private LocalDate date;
    private String subject;
    private String goal;
    private Boolean isCompleted;
    private String comment;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .mentee(todo.getMentee())
                .creator(todo.getCreator())
                .title(todo.getTitle())
                .date(todo.getDate())
                .subject(todo.getSubject())
                .goal(todo.getGoal())
                .isCompleted(todo.getIsCompleted())
                .comment(todo.getComment())
                .build();
    }
}
