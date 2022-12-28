package com.awesometesting.mapper;

import com.awesometesting.dto.files.ResponseFile;
import com.awesometesting.model.FileDB;
import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@UtilityClass
public class FileMapper {

    public ResponseFile toResponseFile(FileDB dbFile) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(dbFile.getId())
                .toUriString();

        return new ResponseFile(
                dbFile.getName(),
                fileDownloadUri,
                dbFile.getType(),
                dbFile.getData().length);
    }

}
