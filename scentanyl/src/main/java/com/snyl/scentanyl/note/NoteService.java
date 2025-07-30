package com.snyl.scentanyl.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteByName(String name) {
        return noteRepository.findByNameIgnoreCase(name);
    }
}
