package com.fsd.inventopilot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponseData {

    private String fileName;
    private String downloadURL;
    private String contentType;
    private Long fileSize;
}
