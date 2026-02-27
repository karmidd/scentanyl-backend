package com.snyl.scentanyl.perfumer;

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

@WebMvcTest(value = PerfumerController.class, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.snyl.scentanyl.config.TokenFilter.class))
class PerfumerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PerfumerService perfumerService;

    @MockitoBean
    private TokenService tokenService;

    @Test
    @DisplayName("GET /api/perfumers should return all perfumers")
    void getPerfumers_ShouldReturnAllPerfumers() throws Exception {
        // Given
        Perfumer perfumer1 = new Perfumer();
        // Don't set ID - it's auto-generated
        perfumer1.setName("Ernest Beaux");
        perfumer1.setImage("ernest-beaux.jpg");
        perfumer1.setTotalFragrances(25);

        Perfumer perfumer2 = new Perfumer();
        // Don't set ID - it's auto-generated
        perfumer2.setName("Jacques Polge");
        perfumer2.setImage("jacques-polge.jpg");
        perfumer2.setTotalFragrances(75);

        List<Perfumer> perfumers = Arrays.asList(perfumer1, perfumer2);
        when(perfumerService.getPerfumers()).thenReturn(perfumers);

        // When & Then
        mockMvc.perform(get("/api/perfumers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Ernest Beaux")))
                .andExpect(jsonPath("$[0].totalFragrances", is(25)))
                .andExpect(jsonPath("$[1].name", is("Jacques Polge")))
                .andExpect(jsonPath("$[1].totalFragrances", is(75)));

        verify(perfumerService, times(1)).getPerfumers();
    }

    @Test
    @DisplayName("GET /api/perfumers/ should return all perfumers (with trailing slash)")
    void getPerfumers_WithTrailingSlash_ShouldReturnAllPerfumers() throws Exception {
        // Given
        Perfumer perfumer = new Perfumer();
        perfumer.setName("Olivier Creed");
        perfumer.setTotalFragrances(50);

        List<Perfumer> perfumers = Collections.singletonList(perfumer);
        when(perfumerService.getPerfumers()).thenReturn(perfumers);

        // When & Then
        mockMvc.perform(get("/api/perfumers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Olivier Creed")));

        verify(perfumerService, times(1)).getPerfumers();
    }

    @Test
    @DisplayName("GET /api/perfumers should return empty list when no perfumers exist")
    void getPerfumers_ShouldReturnEmptyList_WhenNoPerfumersExist() throws Exception {
        // Given
        when(perfumerService.getPerfumers()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/perfumers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(perfumerService, times(1)).getPerfumers();
    }
}