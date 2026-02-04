package com.derabbit.seolstudy.domain.todo;

import java.time.LocalDate;

import com.derabbit.seolstudy.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long creatorId;

    @Column(nullable = false, length = 100)
    private String title;

    private LocalDate date;

    private String subject;

    @Lob
    private String goal;

    private Boolean isCompleted;

    @Lob
    private String comment;

    public static Todo create(
            Long userId,
            Long creatorId,
            String title,
            LocalDate date,
            String subject,
            String goal,
            Boolean isCompleted,
            String comment
    ) {
        Todo todo = new Todo();
        todo.userId = userId;
        todo.creatorId = creatorId;
        todo.title = title;
        todo.date = date;
        todo.subject = subject;
        todo.goal = goal;
        todo.isCompleted = (isCompleted != null) ? isCompleted : Boolean.FALSE;
        todo.comment = comment;
        return todo;
    }

    public void updateByMentee(
            String title,
            LocalDate date,
            String subject,
            String goal,
            Boolean isCompleted
    ) {
        this.title = title;
        this.date = date;
        this.subject = subject;
        this.goal = goal;
        if (isCompleted != null) {
            this.isCompleted = isCompleted;
        }
    }

    public void updateByMentor(
            String title,
            LocalDate date,
            String subject,
            String goal
    ) {
        this.title = title;
        this.date = date;
        this.subject = subject;
        this.goal = goal;
    }
}
