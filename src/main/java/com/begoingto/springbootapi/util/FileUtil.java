package com.begoingto.springbootapi.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Component
public class FileUtil {
    @Value("${file.server_path}")
    private String fileServerPath;

    @Value("${file.client_path}")
    private String fileClientPath;

    @Value("${file.base_url}")
    private String baseUrl;

    public Resource findByName(String name){
        Path path = Paths.get(fileServerPath + name);
        try{
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()){
                return resource;
            }
        }catch (MalformedURLException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File not found.");
    }

}
