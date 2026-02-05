package com.derabbit.seolstudy.domain.study.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derabbit.seolstudy.domain.study.StudySession;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {

    List<StudySession> findAllByUser_IdAndDateOrderByStartAtAsc(Long userId, LocalDate date);

    Optional<StudySession> findByIdAndUser_Id(Long id, Long userId);

    boolean existsByUser_IdAndEndAtIsNull(Long userId);
}
