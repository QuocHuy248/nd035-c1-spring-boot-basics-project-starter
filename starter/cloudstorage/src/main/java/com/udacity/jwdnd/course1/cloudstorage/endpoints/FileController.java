package com.udacity.jwdnd.course1.cloudstorage.endpoints;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.unit.DataSize;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/files")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @Value("${files.max-file-size}")
    private DataSize maximumFileSize;
    @PostMapping("/save")
    public String fileUpload(@RequestParam("fileUpload")MultipartFile multipartFile, Model model, Authentication authentication) throws IOException {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        String fileName = multipartFile.getOriginalFilename();
        if (!fileService.fileIsAvailable(fileName, userId)) {
            model.addAttribute("success", false);
            model.addAttribute("message", "File already exists");
        } else {
            String contentType = multipartFile.getContentType();
            Long fileSize = multipartFile.getSize();
            byte[] fileData = multipartFile.getBytes();

            if (multipartFile.getSize() <= maximumFileSize.toBytes()) {
                fileService.uploadFile(new File(null, fileName, contentType, fileSize, userId, fileData));
                model.addAttribute("success", true);
            }
        }
        return "result";
    }

    @GetMapping("/view/{fileId}")
    public void viewFile(@PathVariable("fileId") Integer fileId, HttpServletResponse response, Authentication authentication) throws IOException {
        User currentUser = userService.getUser(authentication.getName());
        File file = fileService.getFile(fileId);

        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "filename=\"" + file.getFilename() + "\"");
        response.setContentLengthLong(file.getFileSize());

        OutputStream ops = response.getOutputStream();
        try {
            ops.write(file.getFileData(), 0, file.getFileData().length);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            ops.close();
        }
    }
    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId) {
        fileService.deleteFile(fileId);
        return "redirect:/result";
    }
}