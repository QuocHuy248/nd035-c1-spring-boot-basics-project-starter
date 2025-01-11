package com.udacity.jwdnd.course1.cloudstorage.endpoints;

import com.udacity.jwdnd.course1.cloudstorage.enums.ConstUdacity;
import com.udacity.jwdnd.course1.cloudstorage.enums.UriParam;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping(value = {UriParam.HOME})
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model,@ModelAttribute("file") File file, @ModelAttribute("note") Note note,@ModelAttribute("credential") Credential credential) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        List<File> files = fileService.getFiles(userId);
        model.addAttribute(ConstUdacity.MODEL_ATTRIBUTE_FILES, files);
        List<Note> noteList = noteService.getNotes(userId);
        model.addAttribute("notes", noteList);
        List<Credential> credentialList = credentialService.getCredentials(userId);
        model.addAttribute("credentialList", credentialList);
        model.addAttribute("encryptionService", encryptionService);
        return ConstUdacity.HOME_VIEW;
    }
}