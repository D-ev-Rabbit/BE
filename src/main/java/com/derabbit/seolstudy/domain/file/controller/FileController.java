package com.derabbit.seolstudy.domain.file.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.derabbit.seolstudy.domain.file.dto.response.FileResponse;
import com.derabbit.seolstudy.domain.file.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload/{todoId}")
    public ResponseEntity<FileResponse> uploadFile(
            @PathVariable Long todoId,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();

        FileResponse response = fileService.upload(todoId, file, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/todos/{todoId}/mentor")
    public ResponseEntity<List<FileResponse>> getFilesByTodo(
            @PathVariable Long todoId,
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                fileService.getMentorFilesByTodo(todoId, userId)
        );
    }

}
