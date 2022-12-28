package com.awesometesting.service;

import com.awesometesting.model.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public interface FileStorageService {

    FileDB store(MultipartFile file) throws IOException;

    Optional<FileDB> getFile(String id);

    Stream<FileDB> getAllFiles();
}
