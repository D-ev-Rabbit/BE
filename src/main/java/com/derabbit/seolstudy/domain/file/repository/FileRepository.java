package com.derabbit.seolstudy.domain.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.derabbit.seolstudy.domain.file.File;
import com.derabbit.seolstudy.domain.todo.Todo;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByTodoOrderByCreatedAtAsc(Todo todo);

    List<File> findByTodo_Id(Long todoId);

    List<File> findAllByTodo_IdAndCreator_Id(Long todoId, Long creatorId);

    List<File> findByTodo_IdInAndCreator_Id(Iterable<Long> todoIds, Long creatorId);

    /** todo별 creator별 파일 개수 (목록에서 멘티 업로드 파일 수 표시용) */
    @Query("SELECT f.todo.id, f.creator.id, COUNT(f) FROM File f WHERE f.todo.id IN :todoIds GROUP BY f.todo.id, f.creator.id")
    List<Object[]> countByTodoIdAndCreatorId(@Param("todoIds") List<Long> todoIds);
}
