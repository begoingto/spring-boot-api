package com.begoingto.springbootapi.api.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    /**
     * uses to upload a single file
     * @param file request form data form client
     * @return FileDto
     */
    FileDto uploadSingle(MultipartFile file);

    /**
     * uses to upload a multiple files
     * @param files request form data form client
     * @return List<FileDto>
     */
    List<FileDto> multipleUpload(List<MultipartFile> files);

    List<FileDto> getAllFile();
}
