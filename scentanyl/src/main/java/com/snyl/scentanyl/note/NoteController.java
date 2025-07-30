package com.snyl.scentanyl.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping({"/notes", "/notes/"})
    public List<Note> getNotes() {
        return noteService.getNotes();
    }
}
