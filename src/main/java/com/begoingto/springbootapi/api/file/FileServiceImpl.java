package com.begoingto.springbootapi.api.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
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
        String size = String.valueOf(file.getSize()/1024).concat(" KB");
        String newFile = String.format("%s%s%s",uuid,".",ext);
        String url = String.format("%s/files/%s",baseUrl,newFile);
        String download = baseUrl + "/api/v1/files/download/" + newFile;

        Path paths = Paths.get(fileServerPath+"/"+ newFile);
        try {
            Files.copy(file.getInputStream(),paths);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"upload file failed, please contact your self.");
        }
        return new FileDto(newFile,url,ext,size,download);
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
                String url = this.baseUrl+ "/files/" + name;
                int lastIndex = name.lastIndexOf('.');
                String ext = name.substring(lastIndex+1);
                String size = String.valueOf(file.length()/1024).concat(" KB");
                String download = baseUrl + "/api/v1/files/download/" + name;
                fileDtoList.add(new FileDto(name, url, ext, size,download));
            }
        }

        return fileDtoList;
    }

    @Override
    public FileDto deleteByName(String filename) {
        FileDto fileDto = getFileDtoByName(filename);
        File file = new File(this.fileServerPath, filename);
        boolean delete = file.delete();
        return fileDto;
    }

    @Override
    public boolean deleteAllFile() {
        if (this.getAllFile().isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File is empty.");
        this.getAllFile().forEach(fileDto -> this.deleteByName(fileDto.name()));
        return true;
    }

    @Override
    public Resource downloadFile(String filename) {
        Resource resource = new PathResource(fileServerPath + filename);
        if (!resource.exists() || !resource.isReadable()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found file");
        }
        return resource;
    }

    private FileDto getFileDtoByName(String filename) {
        FileDto fileDto = this.getAllFile().stream()
                .filter(fileDto1 -> fileDto1.name().equalsIgnoreCase(filename))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not be found, please try gain..."));
        return fileDto;
    }
}
