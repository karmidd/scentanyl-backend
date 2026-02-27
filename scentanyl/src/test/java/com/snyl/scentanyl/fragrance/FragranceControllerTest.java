package com.snyl.scentanyl.fragrance;

import com.snyl.scentanyl.config.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(value = FragranceController.class, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.snyl.scentanyl.config.TokenFilter.class))
class FragranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FragranceService fragranceService;

    @MockitoBean
    private TokenService tokenService;

    @Test
    @DisplayName("GET /api/fragrances should return paginated fragrances")
    void getFragrances_ShouldReturnPaginatedFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setId(1L);
        fragrance.setName("No. 5");
        fragrance.setBrand("Chanel");

        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        Page<Fragrance> page = new PageImpl<>(fragrances, PageRequest.of(0, 50), 1);

        when(fragranceService.getFragrancesFiltered(
                anyInt(), anyInt(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/fragrances"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("No. 5")))
                .andExpect(jsonPath("$.content[0].brand", is("Chanel")))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)));

        verify(fragranceService, times(1)).getFragrancesFiltered(
                anyInt(), anyInt(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
    }

    @Test
    @DisplayName("GET /api/brands/{brand} should return fragrances by brand")
    void getFragrancesByBrand_ShouldReturnBrandFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setName("No. 5");
        fragrance.setBrand("Chanel");
        
        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        when(fragranceService.getFragrancesByBrand("Chanel")).thenReturn(fragrances);

        // When & Then
        mockMvc.perform(get("/api/brands/Chanel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand", is("Chanel")));

        verify(fragranceService, times(1)).getFragrancesByBrand("Chanel");
    }

    @Test
    @DisplayName("GET /api/fragrances/{brand}/{name} should return specific fragrance")
    void getFragrance_ShouldReturnFragrance() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setName("No. 5");
        fragrance.setBrand("Chanel");

        when(fragranceService.getFragranceByBrandAndName("Chanel", "No. 5"))
                .thenReturn(Optional.of(fragrance));

        // When & Then
        mockMvc.perform(get("/api/fragrances/Chanel/No. 5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("No. 5")))
                .andExpect(jsonPath("$.brand", is("Chanel")));

        verify(fragranceService, times(1)).getFragranceByBrandAndName("Chanel", "No. 5");
    }

    @Test
    @DisplayName("GET /api/fragrances/{brand}/{name}/{id} should return fragrance with id")
    void getFragranceWithId_ShouldReturnFragrance() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setId(1L);
        fragrance.setName("No. 5");
        fragrance.setBrand("Chanel");

        when(fragranceService.getFragranceByBrandAndNameAndId("Chanel", "No. 5", 1L))
                .thenReturn(Optional.of(fragrance));

        // When & Then
        mockMvc.perform(get("/api/fragrances/Chanel/No. 5/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("No. 5")));

        verify(fragranceService, times(1)).getFragranceByBrandAndNameAndId("Chanel", "No. 5", 1L);
    }

    @Test
    @DisplayName("GET /api/accords/{accord} should return fragrances by accord")
    void getFragrancesByAccord_ShouldReturnFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setAccords("Floral, Fresh");

        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        Page<Fragrance> page = new PageImpl<>(fragrances, PageRequest.of(0, 50), 1);

        when(fragranceService.getFragrancesByAccordPaginated(
                eq("Floral"), anyInt(), anyInt(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/accords/Floral"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].accords", containsString("Floral")))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)));

        verify(fragranceService, times(1)).getFragrancesByAccordPaginated(
                eq("Floral"), anyInt(), anyInt(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
    }

    @Test
    @DisplayName("GET /api/perfumers/{perfumerName} should return fragrances by perfumer")
    void getFragrancesByPerfumer_ShouldReturnFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setPerfumerNames("Ernest Beaux");

        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        when(fragranceService.getFragrancesByPerfumer("Ernest Beaux")).thenReturn(fragrances);

        // When & Then
        mockMvc.perform(get("/api/perfumers/Ernest Beaux"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].perfumerNames", is("Ernest Beaux")));

        verify(fragranceService, times(1)).getFragrancesByPerfumer("Ernest Beaux");
    }

    @Test
    @DisplayName("GET /api/notes/{note} should return fragrances by note")
    void getFragrancesByNote_ShouldReturnFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setTopNotes("Rose, Jasmine");

        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        Page<Fragrance> page = new PageImpl<>(fragrances, PageRequest.of(0, 50), 1);

        when(fragranceService.getFragrancesByNotePaginated(
                eq("Rose"), any(), anyInt(), anyInt(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/notes/Rose"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].topNotes", containsString("Rose")))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)));

        verify(fragranceService, times(1)).getFragrancesByNotePaginated(
                eq("Rose"), any(), anyInt(), anyInt(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
    }

    @Test
    @DisplayName("GET /api/notes/top/{note} should return fragrances by top note")
    void getFragrancesByTopNote_ShouldReturnFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setTopNotes("Bergamot, Lemon");

        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        when(fragranceService.getFragrancesByTopNote("Bergamot")).thenReturn(fragrances);

        // When & Then
        mockMvc.perform(get("/api/notes/top/Bergamot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(fragranceService, times(1)).getFragrancesByTopNote("Bergamot");
    }

    @Test
    @DisplayName("GET /api/notes/middle/{note} should return fragrances by middle note")
    void getFragrancesByMiddleNote_ShouldReturnFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setMiddleNotes("Jasmine, Rose");

        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        when(fragranceService.getFragrancesByMiddleNote("Jasmine")).thenReturn(fragrances);

        // When & Then
        mockMvc.perform(get("/api/notes/middle/Jasmine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(fragranceService, times(1)).getFragrancesByMiddleNote("Jasmine");
    }

    @Test
    @DisplayName("GET /api/notes/base/{note} should return fragrances by base note")
    void getFragrancesByBaseNote_ShouldReturnFragrances() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setBaseNotes("Vanilla, Musk");

        List<Fragrance> fragrances = Collections.singletonList(fragrance);
        when(fragranceService.getFragrancesByBaseNote("Vanilla")).thenReturn(fragrances);

        // When & Then
        mockMvc.perform(get("/api/notes/base/Vanilla"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(fragranceService, times(1)).getFragrancesByBaseNote("Vanilla");
    }

    @Test
    @DisplayName("GET /api/random-frag should return single random fragrance")
    void getRandomFragrance_WithDefaultCount_ShouldReturnSingleFragrance() throws Exception {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setName("Random Fragrance");

        when(fragranceService.getRandomFragrance()).thenReturn(Optional.of(fragrance));

        // When & Then
        mockMvc.perform(get("/api/random-frag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Random Fragrance")));

        verify(fragranceService, times(1)).getRandomFragrance();
    }

    @Test
    @DisplayName("GET /api/random-frag?count=3 should return multiple fragrances")
    void getRandomFragrance_WithCount_ShouldReturnMultipleFragrances() throws Exception {
        // Given
        Fragrance frag1 = new Fragrance();
        frag1.setName("Frag1");
        Fragrance frag2 = new Fragrance();
        frag2.setName("Frag2");

        List<Fragrance> fragrances = Arrays.asList(frag1, frag2);
        when(fragranceService.getRandomFragrances(2)).thenReturn(fragrances);

        // When & Then
        mockMvc.perform(get("/api/random-frag")
                        .param("count", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(fragranceService, times(1)).getRandomFragrances(2);
    }

    @Test
    @DisplayName("GET /api/random-frag should return 404 when no fragrance found")
    void getRandomFragrance_WhenNoFragranceFound_ShouldReturn404() throws Exception {
        // Given
        when(fragranceService.getRandomFragrance()).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/random-frag"))
                .andExpect(status().isNotFound());

        verify(fragranceService, times(1)).getRandomFragrance();
    }
}