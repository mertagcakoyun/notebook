package com.notebook.Note.controller;

import com.notebook.Note.dto.NoteDto;
import com.notebook.Note.entity.Note;
import com.notebook.Note.service.NoteService;
import com.notebook.User.entity.User;
import com.notebook.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {
    private final NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Autowired
    @Lazy
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<Note> getNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return List.of();
        }
        return noteService.getNotesByUser(user);
    }

    @GetMapping("/{id}")
    public Note getNote(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (!note.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access");
        }
        return note;
    }

    @PostMapping
    public Note addNote(@RequestBody NoteDto noteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        return noteService.addNote(noteDto.getTitle(), noteDto.getContent(), user);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody NoteDto noteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Note note = noteService.getNoteById(id);
        if (!note.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access");
        }
        return noteService.updateNote(id, noteDto.getTitle(), noteDto.getContent(), user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteNote(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Note note = noteService.getNoteById(id);
        if (!note.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access");
        }
        return noteService.deleteNote(id, user);
    }
}
