package com.notebook.Note.repository;


import com.notebook.Note.entity.Note;
import com.notebook.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User user);
}
