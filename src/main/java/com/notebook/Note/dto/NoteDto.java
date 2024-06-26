package com.notebook.Note.dto;

/**
 * Data Transfer Object (DTO) class for representing a note.
 */
public class NoteDto {
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
