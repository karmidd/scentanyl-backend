package com.snyl.scentanyl.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }
}
