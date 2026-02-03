package com.derabbit.seolstudy.domain.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.derabbit.seolstudy.domain.common.BaseTimeEntity;

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
public class Planner extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate date;
    private LocalDateTime timeLanguage;
    private LocalDateTime timeEnglish;
    private LocalDateTime timeMath;

    @Lob
    private String comment;
}
