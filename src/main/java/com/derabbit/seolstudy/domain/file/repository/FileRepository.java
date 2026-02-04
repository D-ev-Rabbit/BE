package com.derabbit.seolstudy.domain.file.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.derabbit.seolstudy.domain.file.File;
import com.derabbit.seolstudy.domain.todo.Todo;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByTodoOrderByCreatedAtAsc(Todo todo);

    

}