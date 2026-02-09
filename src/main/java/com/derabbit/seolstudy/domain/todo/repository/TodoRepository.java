package com.derabbit.seolstudy.domain.todo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.derabbit.seolstudy.domain.todo.Todo;
import com.derabbit.seolstudy.domain.todo.dto.response.TodoWithMine;

public interface TodoRepository extends JpaRepository<Todo, Long> {

        @Query("""
        select new com.yourpackage.TodoWithMine(
                t,
                case when t.creator.id = :userId then true else false end
        )
        from Todo t
        where t.mentee.id = :userId
        and (:date is null or t.date = :date)
        and (:isCompleted is null or t.isCompleted = :isCompleted)
        and (:subject is null or t.subject = :subject)
        order by t.date asc, t.id desc
        """)
        List<TodoWithMine> findAllByUserIdAndFilters(
                @Param("userId") Long userId,
                @Param("date") LocalDate date,
                @Param("isCompleted") Boolean isCompleted,
                @Param("subject") String subject
        );

    List<Todo> findAllByDateAndIsCompleted(LocalDate date, Boolean isCompleted);

    List<Todo> findAllByMentee_IdAndDateBetween(Long menteeId, LocalDate from, LocalDate to);
}
