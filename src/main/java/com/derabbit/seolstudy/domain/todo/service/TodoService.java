package com.derabbit.seolstudy.domain.todo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derabbit.seolstudy.domain.feedback.Feedback;
import com.derabbit.seolstudy.domain.feedback.repository.FeedbackRepository;
import com.derabbit.seolstudy.domain.file.File;
import com.derabbit.seolstudy.domain.file.repository.FileRepository;
import com.derabbit.seolstudy.domain.todo.Todo;
import com.derabbit.seolstudy.domain.todo.dto.request.CreateTodoRequest;
import com.derabbit.seolstudy.domain.todo.dto.request.UpdateTodoRequest;
import com.derabbit.seolstudy.domain.todo.dto.response.TodoDetailResponse;
import com.derabbit.seolstudy.domain.todo.dto.response.TodoFeedbackResponse;
import com.derabbit.seolstudy.domain.todo.dto.response.TodoFileResponse;
import com.derabbit.seolstudy.domain.todo.dto.response.TodoResponse;
import com.derabbit.seolstudy.domain.todo.dto.response.TodoSummaryResponse;
import com.derabbit.seolstudy.domain.todo.repository.TodoRepository;
import com.derabbit.seolstudy.domain.user.User;
import com.derabbit.seolstudy.domain.user.repository.UserRepository;
import com.derabbit.seolstudy.global.exception.CustomException;
import com.derabbit.seolstudy.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public TodoResponse createByMentee(Long menteeId, CreateTodoRequest request) {
        validateCreateRequest(request);

        Todo todo = Todo.create(
                menteeId,
                menteeId,
                request.getTitle().trim(),
                request.getDate(),
                trimToNull(request.getSubject()),
                request.getGoal(),
                request.getIsCompleted(),
                null
        );

        return TodoResponse.from(todoRepository.save(todo));
    }

    @Transactional
    public TodoResponse createByMentor(Long mentorId, Long menteeId, CreateTodoRequest request) {
        validateCreateRequest(request);
        validateMenteeAssignment(mentorId, menteeId);

        Todo todo = Todo.create(
                menteeId,
                mentorId,
                request.getTitle().trim(),
                request.getDate(),
                trimToNull(request.getSubject()),
                request.getGoal(),
                Boolean.FALSE,
                null
        );

        return TodoResponse.from(todoRepository.save(todo));
    }

    public List<TodoSummaryResponse> getMenteeTodos(Long menteeId, LocalDate date, Boolean isCompleted, String subject) {
        return todoRepository.findAllByUserIdAndFilters(menteeId, date, isCompleted, trimToNull(subject))
                .stream()
                .map(TodoSummaryResponse::from)
                .toList();
    }

    public List<TodoSummaryResponse> getMentorMenteeTodos(Long mentorId, Long menteeId, LocalDate date, Boolean isCompleted, String subject) {
        validateMenteeAssignment(mentorId, menteeId);
        return getMenteeTodos(menteeId, date, isCompleted, subject);
    }

    public TodoDetailResponse getMenteeTodoDetail(Long menteeId, Long todoId) {
        Todo todo = getTodoOrThrow(todoId);
        if (!menteeId.equals(todo.getUserId())) {
            throw new CustomException(ErrorCode.TODO_NOT_FOUND);
        }
        return toTodoDetailResponse(todo);
    }

    public TodoDetailResponse getMentorTodoDetail(Long mentorId, Long todoId) {
        Todo todo = getTodoOrThrow(todoId);
        validateMenteeAssignment(mentorId, todo.getUserId());
        return toTodoDetailResponse(todo);
    }

    @Transactional
    public TodoResponse updateByMentee(Long menteeId, Long todoId, UpdateTodoRequest request) {
        validateUpdateRequest(request, true);
        Todo todo = getTodoOrThrow(todoId);

        if (!menteeId.equals(todo.getUserId())) {
            throw new CustomException(ErrorCode.TODO_NOT_FOUND);
        }
        if (!menteeId.equals(todo.getCreatorId())) {
            throw new CustomException(ErrorCode.TODO_EDIT_FORBIDDEN);
        }

        todo.updateByMentee(
                request.getTitle().trim(),
                request.getDate(),
                trimToNull(request.getSubject()),
                request.getGoal(),
                request.getIsCompleted()
        );

        return TodoResponse.from(todo);
    }

    @Transactional
    public TodoResponse updateByMentor(Long mentorId, Long todoId, UpdateTodoRequest request) {
        validateUpdateRequest(request, false);
        Todo todo = getTodoOrThrow(todoId);

        validateMenteeAssignment(mentorId, todo.getUserId());
        if (!mentorId.equals(todo.getCreatorId())) {
            throw new CustomException(ErrorCode.TODO_EDIT_FORBIDDEN);
        }

        todo.updateByMentor(
                request.getTitle().trim(),
                request.getDate(),
                trimToNull(request.getSubject()),
                request.getGoal()
        );

        return TodoResponse.from(todo);
    }

    @Transactional
    public void deleteByMentee(Long menteeId, Long todoId) {
        Todo todo = getTodoOrThrow(todoId);

        if (!menteeId.equals(todo.getUserId())) {
            throw new CustomException(ErrorCode.TODO_NOT_FOUND);
        }
        if (!menteeId.equals(todo.getCreatorId())) {
            throw new CustomException(ErrorCode.TODO_DELETE_FORBIDDEN);
        }

        todoRepository.delete(todo);
    }

    @Transactional
    public void deleteByMentor(Long mentorId, Long todoId) {
        Todo todo = getTodoOrThrow(todoId);

        validateMenteeAssignment(mentorId, todo.getUserId());
        if (!mentorId.equals(todo.getCreatorId())) {
            throw new CustomException(ErrorCode.TODO_DELETE_FORBIDDEN);
        }

        todoRepository.delete(todo);
    }

    private Todo getTodoOrThrow(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new CustomException(ErrorCode.TODO_NOT_FOUND));
    }

    private void validateMenteeAssignment(Long mentorId, Long menteeId) {
        if (menteeId == null) {
            throw new CustomException(ErrorCode.MENTEE_NOT_ASSIGNED);
        }

        User mentee = userRepository.findById(menteeId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (mentee.getMentorId() == null || !mentorId.equals(mentee.getMentorId())) {
            throw new CustomException(ErrorCode.MENTEE_NOT_ASSIGNED);
        }
    }

    private TodoDetailResponse toTodoDetailResponse(Todo todo) {
        return TodoDetailResponse.from(todo, buildFileResponses(todo.getId()));
    }

    private List<TodoFileResponse> buildFileResponses(Long todoId) {
        List<File> files = fileRepository.findAllByTodoIdOrderByIdAsc(todoId);
        if (files.isEmpty()) {
            return List.of();
        }

        List<Long> fileIds = files.stream()
                .map(File::getId)
                .toList();

        Map<Long, List<TodoFeedbackResponse>> feedbackMap = feedbackRepository.findAllByFileIdIn(fileIds)
                .stream()
                .collect(Collectors.groupingBy(
                        Feedback::getFileId,
                        Collectors.mapping(TodoFeedbackResponse::from, Collectors.toList())
                ));

        return files.stream()
                .map(file -> TodoFileResponse.from(file, feedbackMap.getOrDefault(file.getId(), List.of())))
                .toList();
    }

    private void validateCreateRequest(CreateTodoRequest request) {
        if (request == null || isBlank(request.getTitle())) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        if (request.getTitle().length() > 100) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    private void validateUpdateRequest(UpdateTodoRequest request, boolean allowStatusUpdate) {
        if (request == null || isBlank(request.getTitle())) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        if (request.getTitle().length() > 100) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        if (!allowStatusUpdate && request.getIsCompleted() != null) {
            throw new CustomException(ErrorCode.TODO_EDIT_FORBIDDEN);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
