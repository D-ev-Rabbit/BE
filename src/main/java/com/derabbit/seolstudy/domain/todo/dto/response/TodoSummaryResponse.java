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

    public static TodoSummaryResponse from(Todo todo) {
        return TodoSummaryResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .date(todo.getDate())
                .subject(todo.getSubject())
                .isCompleted(todo.getIsCompleted())
                .build();
    }
}
