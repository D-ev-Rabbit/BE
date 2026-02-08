package com.derabbit.seolstudy.domain.file.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.derabbit.seolstudy.domain.file.File;
import com.derabbit.seolstudy.domain.file.File.FileType;
import com.derabbit.seolstudy.domain.file.dto.response.FileResponse;
import com.derabbit.seolstudy.domain.file.repository.FileRepository;
import com.derabbit.seolstudy.domain.todo.Todo;
import com.derabbit.seolstudy.domain.todo.repository.TodoRepository;
import com.derabbit.seolstudy.domain.user.User;
import com.derabbit.seolstudy.domain.user.repository.UserRepository;
import com.derabbit.seolstudy.global.exception.CustomException;
import com.derabbit.seolstudy.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final String FILE_DIR;

    @Transactional
    public FileResponse upload(Long todoId, MultipartFile multipartFile, Long userId) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new CustomException(ErrorCode.TODO_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        FileType fileType = getFileType(multipartFile);

        String name = multipartFile.getOriginalFilename();

        String savedPath = saveToLocal(multipartFile);

        File file = File.of(todo, name ,user, savedPath, fileType);

        File saved = fileRepository.save(file);

        return FileResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getFilesByMentorId(Long todoId, Long mentorId) {

        if (mentorId == null) {
            throw new CustomException(ErrorCode.MENTOR_NOT_FOUND);
        }

        return fileRepository
                .findAllByTodo_IdAndCreator_Id(todoId, mentorId)
                .stream()
                .map(FileResponse::from)
                .toList();
    }

    private FileType getFileType(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new CustomException(ErrorCode.FILE_TYPE_INVALID);
        }
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        return FileType.from(ext);
    }

    private String saveToLocal(MultipartFile multipartFile) {
        try {
            String filename = System.currentTimeMillis() + "_" + UUID.randomUUID() + "_" 
                              + multipartFile.getOriginalFilename();
            String fullPath = Paths.get(FILE_DIR, filename).toString();

            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                fos.write(multipartFile.getBytes());
            }

            return fullPath;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
