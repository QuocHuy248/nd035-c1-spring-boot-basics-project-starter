package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteMapper noteMapper;
    @Override
    public Integer createNote(Note note) {
        return noteMapper.createNote(note);
    }

    @Override
    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    @Override
    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }

    @Override
    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    @Override
    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }
}