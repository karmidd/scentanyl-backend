package com.snyl.scentanyl.note;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note testNote1;
    private Note testNote2;

    @BeforeEach
    void setUp() {
        testNote1 = new Note();
        // Don't set ID - it's auto-generated
        testNote1.setName("Rose");
        testNote1.setTopCount(50);
        testNote1.setMiddleCount(120);
        testNote1.setBaseCount(30);
        testNote1.setUncategorizedCount(5);
        testNote1.setTotalFragrances(205);

        testNote2 = new Note();
        // Don't set ID - it's auto-generated
        testNote2.setName("Vanilla");
        testNote2.setTopCount(10);
        testNote2.setMiddleCount(40);
        testNote2.setBaseCount(150);
        testNote2.setUncategorizedCount(0);
        testNote2.setTotalFragrances(200);
    }

    @Test
    @DisplayName("Should return all notes")
    void getNotes_ShouldReturnAllNotes() {
        // Given
        List<Note> expectedNotes = Arrays.asList(testNote1, testNote2);
        when(noteRepository.findAll()).thenReturn(expectedNotes);

        // When
        List<Note> result = noteService.getNotes();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Rose", result.get(0).getName());
        assertEquals("Vanilla", result.get(1).getName());
        assertEquals(205, result.get(0).getTotalFragrances());
        assertEquals(200, result.get(1).getTotalFragrances());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no notes exist")
    void getNotes_ShouldReturnEmptyList_WhenNoNotesExist() {
        // Given
        when(noteRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Note> result = noteService.getNotes();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should correctly retrieve note counts")
    void getNotes_ShouldHaveCorrectCounts() {
        // Given
        List<Note> expectedNotes = Collections.singletonList(testNote1);
        when(noteRepository.findAll()).thenReturn(expectedNotes);

        // When
        List<Note> result = noteService.getNotes();

        // Then
        Note note = result.getFirst();
        assertEquals(50, note.getTopCount());
        assertEquals(120, note.getMiddleCount());
        assertEquals(30, note.getBaseCount());
        assertEquals(5, note.getUncategorizedCount());
        assertEquals(205, note.getTotalFragrances());
        verify(noteRepository, times(1)).findAll();
    }
}