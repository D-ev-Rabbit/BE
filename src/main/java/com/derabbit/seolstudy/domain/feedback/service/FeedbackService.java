package com.derabbit.seolstudy.domain.feedback.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derabbit.seolstudy.domain.feedback.Feedback;
import com.derabbit.seolstudy.domain.feedback.dto.request.FeedbackRequest;
import com.derabbit.seolstudy.domain.feedback.dto.response.FeedbackResponse;
import com.derabbit.seolstudy.domain.feedback.repository.FeedbackRepository;
import com.derabbit.seolstudy.domain.file.File;
import com.derabbit.seolstudy.domain.file.repository.FileRepository;
import com.derabbit.seolstudy.domain.notification.service.NotificationService;
import com.derabbit.seolstudy.domain.user.User;
import com.derabbit.seolstudy.global.exception.CustomException;
import com.derabbit.seolstudy.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FileRepository fileRepository;
    private final NotificationService notificationService;

    @Transactional
    public FeedbackResponse saveFeedback(FeedbackRequest request) {
        return saveFeedback(null, request.getFileId(), request.getData());
    }

    @Transactional
    public FeedbackResponse saveFeedback(Long mentorId, Long fileId, String data) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        validateMentorAssignment(mentorId, file);

        Feedback feedback = Feedback.of(file, data);
        Feedback saved = feedbackRepository.save(feedback);

        notificationService.createFileFeedbackNotification(file);

        return FeedbackResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public FeedbackResponse getFeedback(Long mentorId, Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        validateMentorAssignment(mentorId, file);

        // 파일에 대한 최신 Feedback 조회
        Feedback feedback = feedbackRepository.findTopByFileOrderByCreatedAtDesc(file)
                .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND));

        return FeedbackResponse.from(feedback);
    }

    private void validateMentorAssignment(Long mentorId, File file) {
        if (mentorId == null) {
            return;
        }
        User mentee = file.getTodo().getMentee();
        if (mentee.getMentorId() == null || !mentorId.equals(mentee.getMentorId())) {
            throw new CustomException(ErrorCode.MENTEE_NOT_ASSIGNED);
        }
    }
}
