package com.derabbit.seolstudy.domain.feedback.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derabbit.seolstudy.domain.feedback.Feedback;
import com.derabbit.seolstudy.domain.feedback.dto.request.FeedbackRequest;
import com.derabbit.seolstudy.domain.feedback.dto.response.FeedbackResponse;
import com.derabbit.seolstudy.domain.feedback.repository.FeedbackRepository;
import com.derabbit.seolstudy.domain.file.File;
import com.derabbit.seolstudy.domain.file.repository.FileRepository;
import com.derabbit.seolstudy.global.exception.CustomException;
import com.derabbit.seolstudy.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FileRepository fileRepository;

    @Transactional
    public FeedbackResponse saveFeedback(FeedbackRequest request) {
        File file = fileRepository.findById(request.getFileId())
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        Feedback feedback = Feedback.of(file, request.getData());
        Feedback saved = feedbackRepository.save(feedback);

        return FeedbackResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public FeedbackResponse getFeedback(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        // 파일에 대한 최신 Feedback 조회
        Feedback feedback = feedbackRepository.findTopByFileOrderByCreatedAtDesc(file)
                .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND));

        return FeedbackResponse.from(feedback);
    }
}
