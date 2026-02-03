package com.derabbit.seolstudy.domain.todo;

import java.time.LocalDate;

import com.derabbit.seolstudy.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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

    private Integer studyMinute;

    private Boolean isCompleted;

    @Lob
    private String comment;
    
}
