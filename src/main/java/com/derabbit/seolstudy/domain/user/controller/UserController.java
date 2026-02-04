package com.derabbit.seolstudy.domain.user.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.derabbit.seolstudy.domain.user.dto.response.MenteeResponse;
import com.derabbit.seolstudy.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")   // 보통 이렇게 씀
public class UserController {

    private final UserService userService;

    @GetMapping("/mentor/mentees")
    public List<MenteeResponse> getMyMentees() {
        Long mentorId = getLoginUserId();
        return userService.getMyMentees(mentorId);
    }

    @PostMapping("/mentor/{menteeId}/assign")
    public void assignMentee(@PathVariable Long menteeId) {
        Long mentorId = getLoginUserId();
        userService.assignMentee(mentorId, menteeId);
    }

    private Long getLoginUserId() {
        return (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
