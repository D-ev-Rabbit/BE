package com.derabbit.seolstudy.domain.user.dto.response;

import lombok.Getter;

@Getter
public class SubjectSummaryResponse {

    private long totalStudySeconds;
    private long todoTotal;
    private long todoCompleted;
    private long feedbackTotal;
    private long feedbackRead;
    private double todoCompletionRate;
    private double feedbackReadRate;

    public void addStudySeconds(long seconds) {
        this.totalStudySeconds += seconds;
    }

    public void incrementTodoTotal() {
        this.todoTotal += 1;
    }

    public void incrementTodoCompleted() {
        this.todoCompleted += 1;
    }

    public void incrementFeedbackTotal() {
        this.feedbackTotal += 1;
    }

    public void incrementFeedbackRead() {
        this.feedbackRead += 1;
    }

    public void computeRates() {
        this.todoCompletionRate = (todoTotal == 0) ? 0.0 : (double) todoCompleted / todoTotal;
        this.feedbackReadRate = (feedbackTotal == 0) ? 0.0 : (double) feedbackRead / feedbackTotal;
    }
}
