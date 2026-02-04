package com.derabbit.seolstudy.domain.todo.dto.response;

import java.util.List;

import com.derabbit.seolstudy.domain.file.File;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoFileResponse {

    private Long fileId;
    private String url;
    private String type;
    private Long version;
    private List<TodoFeedbackResponse> feedbacks;

    public static TodoFileResponse from(File file, List<TodoFeedbackResponse> feedbacks) {
        return TodoFileResponse.builder()
                .fileId(file.getId())
                .url(file.getUrl())
                .type(file.getType())
                .version(file.getVersion())
                .feedbacks(feedbacks)
                .build();
    }
}
