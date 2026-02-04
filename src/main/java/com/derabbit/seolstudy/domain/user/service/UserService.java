package com.derabbit.seolstudy.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derabbit.seolstudy.domain.user.User;
import com.derabbit.seolstudy.domain.user.dto.request.UserUpdateRequest;
import com.derabbit.seolstudy.domain.user.dto.response.MenteeResponse;
import com.derabbit.seolstudy.domain.user.dto.response.UserResponse;
import com.derabbit.seolstudy.domain.user.repository.UserRepository;
import com.derabbit.seolstudy.global.exception.CustomException;
import com.derabbit.seolstudy.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // DTO → 값만 전달 (도메인 순수성 유지)
        user.updateProfile(request.getName(), request.getSchool());
        user.updateGrade(request.getGrade());

        return new UserResponse(
                user.getEmail(),
                user.getName(),
                user.getSchool(),
                user.getGrade(),
                user.getRole().name()
        );
    }

    @Transactional
    public void assignMentee(Long mentorId, Long menteeId) {

        User mentor = userRepository.findById(mentorId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        User mentee = userRepository.findById(menteeId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (mentee.getRole() != User.Role.MENTEE) {
            throw new CustomException(ErrorCode.INVALID_TARGET_USER);
        }

        mentee.assignMentor(mentor);
    }

    @Transactional(readOnly = true)
    public List<MenteeResponse> getMyMentees(Long mentorId) {

        List<User> mentees = userRepository.findAllByMentorId(mentorId);

        return mentees.stream()
                .map(MenteeResponse::from)
                .toList();
    }
}
