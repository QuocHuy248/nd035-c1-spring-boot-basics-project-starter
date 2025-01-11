package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;

import java.util.List;

public interface NoteService {
    Integer createNote(Note note);
    void updateNote(Note note);
    void deleteNote(Integer noteId);
    List<Note> getNotes(Integer userId);
    Note getNote(Integer noteId);
}