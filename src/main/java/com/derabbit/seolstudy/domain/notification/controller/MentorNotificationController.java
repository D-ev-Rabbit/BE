package com.derabbit.seolstudy.domain.notification.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.derabbit.seolstudy.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentor/notifications")
public class MentorNotificationController {

    private final NotificationService notificationService;

    @PostMapping("/feedback/{fileId}")
    public void sendFeedbackNotification(@PathVariable("fileId") Long fileId) {
        notificationService.sendFileFeedbackNotification(fileId);
    }
}
