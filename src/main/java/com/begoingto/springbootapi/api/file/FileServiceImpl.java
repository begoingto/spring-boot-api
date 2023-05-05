package com.begoingto.springbootapi.api.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
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

    @Override
    public List<FileDto> getAllFile() {
        List<FileDto> fileDtoList = new ArrayList<>();

        File folder = new File(this.fileServerPath);
        File[] files = folder.listFiles();

        for (File file : files){
            if (file.isFile()){
                String name = file.getName();
                String url = this.baseUrl + name;
                int lastIndex = name.lastIndexOf('.');
                String ext = name.substring(lastIndex+1);
                long size = file.length();
                fileDtoList.add(new FileDto(name, url, ext, size));
            }
        }

        return fileDtoList;
    }

    @Override
    public FileDto deleteByName(String filename) {

        FileDto fileDto = this.getAllFile().stream()
                .filter(fileDto1 -> fileDto1.name().equalsIgnoreCase(filename))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not be found, please try gain..."));

        File file = new File(this.fileServerPath, filename);
        file.delete();
        return fileDto;
    }
}
