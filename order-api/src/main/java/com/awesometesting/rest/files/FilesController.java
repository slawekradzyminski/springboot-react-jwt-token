package com.awesometesting.rest.files;

import com.awesometesting.dto.files.FilesResponseMessage;
import com.awesometesting.dto.files.ResponseFile;
import com.awesometesting.mapper.FileMapper;
import com.awesometesting.model.FileDB;
import com.awesometesting.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.awesometesting.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequiredArgsConstructor
public class FilesController {

    private final FileStorageService storageService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/files")
    public List<ResponseFile> getListFiles() {
        return storageService.getAllFiles()
                .map(FileMapper::toResponseFile)
                .toList();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/files/{id}")
    public byte[] getFile(@PathVariable String id, HttpServletResponse response) {
        Optional<FileDB> fileDB = storageService.getFile(id);

        if (fileDB.isEmpty()) {
            throw new FileNotFoundException();
        }

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.get().getName() + "\"");
        return fileDB.get().getData();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PostMapping("/files/upload")
    public FilesResponseMessage uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            storageService.store(file);
        } catch (Exception e) {
            throw new FileUploadFailureException("Could not upload the file: " + file.getOriginalFilename() + "!");
        }

        return new FilesResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename());
    }

}
