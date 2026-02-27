package com.snyl.scentanyl.note;

import com.snyl.scentanyl.config.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(value = NoteController.class, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.snyl.scentanyl.config.TokenFilter.class))
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @MockitoBean
    private TokenService tokenService;

    @Test
    @DisplayName("GET /api/notes should return all notes")
    void getNotes_ShouldReturnAllNotes() throws Exception {
        // Given
        Note note1 = new Note();
        // ID is auto-generated
        note1.setName("Rose");
        note1.setTopCount(50);
        note1.setMiddleCount(120);
        note1.setBaseCount(30);
        note1.setUncategorizedCount(5);
        note1.setTotalFragrances(205);

        Note note2 = new Note();
        // ID is auto-generated
        note2.setName("Vanilla");
        note2.setTopCount(10);
        note2.setMiddleCount(40);
        note2.setBaseCount(150);
        note2.setUncategorizedCount(0);
        note2.setTotalFragrances(200);

        List<Note> notes = Arrays.asList(note1, note2);
        when(noteService.getNotes()).thenReturn(notes);

        // When & Then
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Rose")))
                .andExpect(jsonPath("$[0].totalFragrances", is(205)))
                .andExpect(jsonPath("$[0].topCount", is(50)))
                .andExpect(jsonPath("$[0].middleCount", is(120)))
                .andExpect(jsonPath("$[0].baseCount", is(30)))
                .andExpect(jsonPath("$[1].name", is("Vanilla")))
                .andExpect(jsonPath("$[1].totalFragrances", is(200)));

        verify(noteService, times(1)).getNotes();
    }

    @Test
    @DisplayName("GET /api/notes/ should return all notes (with trailing slash)")
    void getNotes_WithTrailingSlash_ShouldReturnAllNotes() throws Exception {
        // Given
        Note note = new Note();
        note.setName("Jasmine");
        note.setTotalFragrances(100);

        List<Note> notes = Collections.singletonList(note);
        when(noteService.getNotes()).thenReturn(notes);

        // When & Then
        mockMvc.perform(get("/api/notes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Jasmine")));

        verify(noteService, times(1)).getNotes();
    }

    @Test
    @DisplayName("GET /api/notes should return empty list when no notes exist")
    void getNotes_ShouldReturnEmptyList_WhenNoNotesExist() throws Exception {
        // Given
        when(noteService.getNotes()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(noteService, times(1)).getNotes();
    }
}