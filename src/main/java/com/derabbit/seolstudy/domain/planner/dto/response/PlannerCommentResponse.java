package com.derabbit.seolstudy.domain.planner.dto.response;

import java.time.LocalDate;

import com.derabbit.seolstudy.domain.planner.Planner;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlannerCommentResponse {

    private Long plannerId;
    private LocalDate date;
    private String comment;

    public static PlannerCommentResponse from(Planner planner) {
        return PlannerCommentResponse.builder()
                .plannerId(planner.getId())
                .date(planner.getDate())
                .comment(planner.getComment())
                .build();
    }
}
