package com.derabbit.seolstudy.domain.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derabbit.seolstudy.domain.feedback.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByFileIdIn(List<Long> fileIds);
}
