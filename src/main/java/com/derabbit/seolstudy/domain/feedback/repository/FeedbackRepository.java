package com.derabbit.seolstudy.domain.feedback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derabbit.seolstudy.domain.feedback.Feedback;
import com.derabbit.seolstudy.domain.file.File;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByFileIn(List<File> fileIds);
    
    Optional<Feedback> findTopByFileOrderByCreatedAtDesc(File file);
    
}
