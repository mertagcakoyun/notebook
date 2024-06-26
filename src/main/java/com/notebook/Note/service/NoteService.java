package com.notebook.Note.service;

import com.notebook.Note.entity.Note;
import com.notebook.Note.repository.NoteRepository;
import com.notebook.User.entity.User;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
/**
 * Service class that provides methods for managing notes, including adding, retrieving,
 * updating, and deleting notes. It interacts with the NoteRepository to perform database
 * operations and ensures that operations are restricted to the authenticated user's notes.
 */
@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note addNote(String title, String content, User user) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);
        return noteRepository.save(note);
    }

    public List<Note> getNotesByUser(User user) {
        return noteRepository.findAllByUser(user);
    }


    public Note getNoteById(Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isPresent()) {
            return noteOptional.get();
        } else {
            throw new NotFoundException("Note with id " + id + " not found");
        }
    }

    public Note updateNote(Long noteId, String title, String content, User user) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null || !note.getUser().equals(user)) {
            return null;
        }
        note.setTitle(title);
        note.setContent(content);
        return noteRepository.save(note);
    }

    public boolean deleteNote(Long noteId, User user) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null || !note.getUser().equals(user)) {
            return false;
        }
        noteRepository.delete(note);
        return true;
    }
}
