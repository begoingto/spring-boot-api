package com.begoingto.springbootapi.api.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Value("${file.server_path}")
    private String fileServerPath;

    @Value("${file.client_path}")
    private String fileClientPath;

    @Value("${file.base_url}")
    private String baseUrl;

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf('.');
        String ext = file.getOriginalFilename().substring(index+1);
        String uuid = UUID.randomUUID().toString();
        long size = file.getSize();
        String newFile = String.format("%s%s%s",uuid,".",ext);
        String url = String.format("%s/files/%s",baseUrl,newFile);

        Path paths = Paths.get(fileServerPath + newFile);
        try {
            Files.copy(file.getInputStream(),paths);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"upload file failed, please contact your self.");
        }
        return new FileDto(newFile,url,ext,size);
    }

    @Override
    public List<FileDto> multipleUpload(List<MultipartFile> files) {
        List<FileDto> fileDtoList = new ArrayList<>();
        for (MultipartFile file : files){
            fileDtoList.add(this.uploadSingle(file));
        }
        return fileDtoList;
    }
}
