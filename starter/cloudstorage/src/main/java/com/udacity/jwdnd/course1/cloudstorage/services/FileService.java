package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;

import java.util.List;

public interface FileService {
    boolean fileIsAvailable(String fileName, Integer userId);
    Integer uploadFile(File file);
    List<File> getFiles(Integer userId);
    File getFile(Integer fileId);
    void deleteFile(Integer fileId);
}