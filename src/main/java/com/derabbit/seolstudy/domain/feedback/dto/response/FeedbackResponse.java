package com.derabbit.seolstudy.domain.feedback.dto.response;

import com.derabbit.seolstudy.domain.feedback.Feedback;
import lombok.Getter;

@Getter
public class FeedbackResponse {
    private Long id;
    private String data;

    public static FeedbackResponse from(Feedback feedback) {
        FeedbackResponse res = new FeedbackResponse();
        res.id = feedback.getId();
        res.data = feedback.getData();
        return res;
    }
}
