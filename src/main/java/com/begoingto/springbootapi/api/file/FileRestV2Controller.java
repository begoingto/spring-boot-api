package com.begoingto.springbootapi.api.file;

import com.begoingto.springbootapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/files")
@RequiredArgsConstructor
public class FileRestV2Controller {
    private final FileService fileService;

    @Value("${file.base_url}")
    private String base_url;

    @GetMapping("/{filename}")
    public BaseRest<?> findByName(@PathVariable String filename) throws IOException {
        FileDto fileDto = fileService.fileByNameV2(filename);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Files has been uploaded.")
                .data(fileDto)
                .build();
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename){
        Resource resource = fileService.downloadFileV2(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition",String.format("attachment;filename=\"%s\"",resource.getFilename()))
                .body(resource);
    }
}
