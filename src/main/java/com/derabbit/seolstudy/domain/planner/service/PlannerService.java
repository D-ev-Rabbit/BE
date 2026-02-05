package com.derabbit.seolstudy.domain.planner.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derabbit.seolstudy.domain.notification.service.NotificationService;
import com.derabbit.seolstudy.domain.planner.Planner;
import com.derabbit.seolstudy.domain.planner.dto.response.PlannerCommentResponse;
import com.derabbit.seolstudy.domain.planner.repository.PlannerRepository;
import com.derabbit.seolstudy.domain.user.User;
import com.derabbit.seolstudy.domain.user.repository.UserRepository;
import com.derabbit.seolstudy.global.exception.CustomException;
import com.derabbit.seolstudy.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public PlannerCommentResponse updateCommentByMentor(Long mentorId, Long plannerId, String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        User mentee = userRepository.findById(planner.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        validateMenteeAssignment(mentorId, mentee);

        planner.updateComment(comment);
        notificationService.createPlannerCommentNotification(mentee, planner);

        return PlannerCommentResponse.from(planner);
    }

    private void validateMenteeAssignment(Long mentorId, User mentee) {
        if (mentee.getId() == null) {
            throw new CustomException(ErrorCode.MENTEE_NOT_ASSIGNED);
        }

        if (mentee.getMentorId() == null || !mentorId.equals(mentee.getMentorId())) {
            throw new CustomException(ErrorCode.MENTEE_NOT_ASSIGNED);
        }
    }
}
