package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {
    private Integer fileId;
    private String filename;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[]fileData;
}