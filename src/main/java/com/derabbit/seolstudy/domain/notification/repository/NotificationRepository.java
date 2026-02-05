package com.derabbit.seolstudy.domain.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derabbit.seolstudy.domain.notification.Notification;
import com.derabbit.seolstudy.domain.notification.NotificationType;
import com.derabbit.seolstudy.domain.todo.Todo;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserIdAndIsReadOrderByCreatedAtDesc(Long userId, Boolean isRead);

    List<Notification> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Notification> findByIdAndUserId(Long id, Long userId);

    boolean existsByTypeAndTodo(NotificationType type, Todo todo);

    void deleteByTypeAndTodo(NotificationType type, Todo todo);
}
