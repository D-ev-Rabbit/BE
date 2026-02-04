package com.derabbit.seolstudy.domain.todo;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.derabbit.seolstudy.domain.common.BaseTimeEntity;
import com.derabbit.seolstudy.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creatorId;

    @Column(nullable = false, length = 100)
    private String title;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Column(nullable = false, length = 200)
    private String goal;

    private Integer studyMinute;

    @Column(nullable = false)
    private Boolean isCompleted = false;

    @Lob
    private String comment;

    public enum Subject {
        KOREAN, ENGLISH, MATH
    }
}
