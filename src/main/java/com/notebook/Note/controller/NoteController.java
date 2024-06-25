package com.notebook.Note.controller;

import com.notebook.Note.entity.Note;
import com.notebook.Note.service.NoteService;
import com.notebook.User.entity.User;
import com.notebook.User.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping()
    public List<Note> getNotes(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return List.of();
        }
        return noteService.getNotesByUser(user);
    }

    @GetMapping("/{id}")
    public Note getNote(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }
    @PostMapping
    public Note addNote(@RequestParam String username, @RequestBody NoteDto noteDto) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        return noteService.addNote(noteDto.getTitle(), noteDto.getContent(), user);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestParam String username, @RequestBody NoteDto noteDto) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        return noteService.updateNote(id, noteDto.getTitle(), noteDto.getContent(), user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteNote(@PathVariable Long id, @RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return false;
        }
        return noteService.deleteNote(id, user);
    }
}

class NoteDto {
    private String title;

    private String content;

    public NoteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
