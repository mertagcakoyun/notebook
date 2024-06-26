package com.notebook.Note.service;

import com.notebook.Note.entity.Note;
import com.notebook.Note.repository.NoteRepository;
import com.notebook.User.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private User user;
    private Note note;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");
        note.setContent("This is a test note.");
        note.setUser(user);
    }

    @Test
    void shouldAddNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note savedNote = noteService.addNote("Test Note", "This is a test note.", user);

        assertNotNull(savedNote);
        assertEquals(note.getTitle(), savedNote.getTitle());
        assertEquals(note.getContent(), savedNote.getContent());
        assertEquals(note.getUser(), savedNote.getUser());
    }

    @Test
    void shouldGetNotesByUser() {
        when(noteRepository.findAllByUser(any(User.class))).thenReturn(List.of(note));

        List<Note> notes = noteService.getNotesByUser(user);

        assertNotNull(notes);
        assertTrue(notes.contains(note));
    }

    @Test
    void shouldGetNoteById() {
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        Note foundNote = noteService.getNoteById(1L);

        assertNotNull(foundNote);
        assertEquals(note.getTitle(), foundNote.getTitle());
        assertEquals(note.getContent(), foundNote.getContent());
        assertEquals(note.getUser(), foundNote.getUser());
    }

    @Test
    void shouldThrowExceptionIfNoteNotFoundById() {
        when(noteRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            noteService.getNoteById(1L);
        });

        assertEquals("Note with id 1 not found", exception.getMessage());
    }

    @Test
    void shouldUpdateNote() {
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note updatedNote = noteService.updateNote(1L, "Updated Note", "This is an updated note.", user);

        assertNotNull(updatedNote);
        assertEquals("Updated Note", updatedNote.getTitle());
        assertEquals("This is an updated note.", updatedNote.getContent());
    }

    @Test
    void shouldNotUpdateNoteIfUserIsDifferent() {
        User differentUser = new User();
        differentUser.setId(2L);
        differentUser.setUsername("differentuser");

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        Note updatedNote = noteService.updateNote(1L, "Updated Note", "This is an updated note.", differentUser);

        assertNull(updatedNote);
    }

    @Test
    void shouldDeleteNote() {
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).delete(any(Note.class));

        boolean isDeleted = noteService.deleteNote(1L, user);

        assertTrue(isDeleted);
        verify(noteRepository, times(1)).delete(note);
    }

    @Test
    void shouldNotDeleteNoteIfUserIsDifferent() {
        User differentUser = new User();
        differentUser.setId(2L);
        differentUser.setUsername("differentuser");

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        boolean isDeleted = noteService.deleteNote(1L, differentUser);

        assertFalse(isDeleted);
        verify(noteRepository, never()).delete(note);
    }
}
