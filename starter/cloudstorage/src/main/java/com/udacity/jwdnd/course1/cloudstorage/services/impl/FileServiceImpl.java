package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;

    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public boolean fileIsAvailable(String fileName, Integer userId) {
        return fileMapper.getFileByName(fileName, userId) == null;
    }

    @Override
    public Integer uploadFile(File file) {
        return fileMapper.insert(file);
    }

    @Override
    public List<File> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    @Override
    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    @Override
    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }
}