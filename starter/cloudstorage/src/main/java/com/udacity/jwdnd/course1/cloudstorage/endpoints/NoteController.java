package com.udacity.jwdnd.course1.cloudstorage.endpoints;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @PostMapping("/notes")
    public String createOrUpdate(@ModelAttribute Note note, Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();

        if(note.getNoteId() == null) {
            note.setUserId(userId);
            noteService.createNote(note);
        } else {
            noteService.updateNote(note);
        }

        model.addAttribute("success", true);
        return "redirect:/result";
    }
    @RequestMapping("/notes/delete/{noteid}")
    public String delete(@PathVariable("noteid") Integer noteId) {
        noteService.deleteNote(noteId);
        return "redirect:/result";
    }
}