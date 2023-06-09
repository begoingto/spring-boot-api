package com.begoingto.springbootapi.api.file;

import com.begoingto.springbootapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;

    @GetMapping
    public BaseRest<?> get(){
        List<FileDto> fileDtoList = fileService.getAllFile();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been got all.")
                .timestamp(LocalDateTime.now())
                .data(fileDtoList)
                .build();
    }

    @DeleteMapping("/{filename}")
    public BaseRest<?> deleteFileByName(@PathVariable String filename){
        FileDto fileDto = fileService.deleteByName(filename);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been deleted.")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename){
        Resource resource = fileService.downloadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment; filename=\"%s\"",resource.getFilename()))
                .body(resource);
    }

    @DeleteMapping
    public BaseRest<?> deleteAllFile(){
        boolean deleted = fileService.deleteAllFile();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been deleted.")
                .timestamp(LocalDateTime.now())
                .data(deleted)
                .build();
    }

    @PostMapping
    public BaseRest<?> uploadSingle(@RequestPart MultipartFile file){
        FileDto fileDto= fileService.uploadSingle(file);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been uploaded.")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

    @PostMapping("/multiple")
    public BaseRest<?> uploadSingle(@RequestPart List<MultipartFile> files){
        List<FileDto> fileDto= fileService.multipleUpload(files);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Files has been uploaded.")
                .data(fileDto)
                .build();
    }
}
