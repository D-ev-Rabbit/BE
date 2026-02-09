package com.derabbit.seolstudy.domain.todo.dto.response;

import java.time.LocalDate;

import com.derabbit.seolstudy.domain.todo.Todo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoSummaryResponse {

    private Long id;
    private String title;
    private LocalDate date;
    private String subject;
    private Boolean isCompleted;
    private Boolean isMine;   // 추가

    public static TodoSummaryResponse from(Todo todo, Long userId) {
        return TodoSummaryResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .date(todo.getDate())
                .subject(todo.getSubject())
                .isCompleted(todo.getIsCompleted())
                .isMine(todo.getCreator().getId().equals(userId)) // 추가
                .build();
    }

    public static TodoSummaryResponse from(TodoWithMine t) {
        Todo todo = t.todo();

        return TodoSummaryResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .date(todo.getDate())
                .subject(todo.getSubject())
                .isCompleted(todo.getIsCompleted())
                .isMine(t.isMine())
                .build();
    }
}
