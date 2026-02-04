package com.derabbit.seolstudy.domain.file.controller;

import com.derabbit.seolstudy.domain.file.dto.response.FileResponse;
import com.derabbit.seolstudy.domain.file.service.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
