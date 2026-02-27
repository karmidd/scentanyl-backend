package com.snyl.scentanyl.accord;

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

@WebMvcTest(value = AccordController.class, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.snyl.scentanyl.config.TokenFilter.class))
class AccordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccordService accordService;

    @MockitoBean
    private TokenService tokenService;

    @Test
    @DisplayName("GET /api/accords should return all accords")
    void getAccords_ShouldReturnAllAccords() throws Exception {
        // Given
        Accord accord1 = new Accord();
        // ID is auto-generated
        accord1.setName("Floral");
        accord1.setTotalFragrances(500);

        Accord accord2 = new Accord();
        // ID is auto-generated
        accord2.setName("Woody");
        accord2.setTotalFragrances(350);

        List<Accord> accords = Arrays.asList(accord1, accord2);
        when(accordService.getAccords()).thenReturn(accords);

        // When & Then
        mockMvc.perform(get("/api/accords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Floral")))
                .andExpect(jsonPath("$[0].totalFragrances", is(500)))
                .andExpect(jsonPath("$[1].name", is("Woody")))
                .andExpect(jsonPath("$[1].totalFragrances", is(350)));

        verify(accordService, times(1)).getAccords();
    }

    @Test
    @DisplayName("GET /api/accords/ should return all accords (with trailing slash)")
    void getAccords_WithTrailingSlash_ShouldReturnAllAccords() throws Exception {
        // Given
        Accord accord = new Accord();
        accord.setName("Fresh");
        accord.setTotalFragrances(250);

        List<Accord> accords = Collections.singletonList(accord);
        when(accordService.getAccords()).thenReturn(accords);

        // When & Then
        mockMvc.perform(get("/api/accords/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Fresh")));

        verify(accordService, times(1)).getAccords();
    }

    @Test
    @DisplayName("GET /api/accords should return empty list when no accords exist")
    void getAccords_ShouldReturnEmptyList_WhenNoAccordsExist() throws Exception {
        // Given
        when(accordService.getAccords()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/accords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(accordService, times(1)).getAccords();
    }
}