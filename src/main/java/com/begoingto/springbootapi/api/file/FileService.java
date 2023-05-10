package com.begoingto.springbootapi.api.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /**
     *  this method to get all file in directory
     * @return List<FileDto>
     */
    List<FileDto> getAllFile();

    /**
     * The method use to delete file within directory by name of file
     * @param filename it's parameter of file name
     * @return FileDto
     */
    FileDto deleteByName(String filename);

    /**
     *  the method using to delete all file within directory
     * @return boolean
     */
    boolean deleteAllFile();

    /**
     * Download file by name
     * @param filename passing parameter
     * @return FileDto
     */
    Resource downloadFile(String filename);

    FileDto fileByNameV2(String name) throws IOException;
    Resource downloadFileV2(String name);
}
