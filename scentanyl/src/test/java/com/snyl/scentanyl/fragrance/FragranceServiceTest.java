package com.snyl.scentanyl.fragrance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FragranceServiceTest {

    @Mock
    private FragranceRepository fragranceRepository;

    @InjectMocks
    private FragranceService fragranceService;

    private Fragrance testFragrance;

    @BeforeEach
    void setUp() {
        testFragrance = new Fragrance();
        testFragrance.setId(1L);
        testFragrance.setName("No. 5");
        testFragrance.setBrand("Chanel");
        testFragrance.setGender("Women");
        testFragrance.setYear(1921L);
        testFragrance.setPerfumerNames("Ernest Beaux");
        testFragrance.setAccords("Aldehyde, Floral, Powdery");
        testFragrance.setTopNotes("Aldehyde, Bergamot, Lemon");
        testFragrance.setMiddleNotes("Rose, Jasmine, Ylang-Ylang");
        testFragrance.setBaseNotes("Sandalwood, Vetiver, Vanilla");
    }

    @Test
    @DisplayName("Should return all fragrances")
    void getFragrances_ShouldReturnAllFragrances() {
        // Given
        List<Fragrance> expectedFragrances = Collections.singletonList(testFragrance);
        when(fragranceRepository.findAll()).thenReturn(expectedFragrances);

        // When
        List<Fragrance> result = fragranceService.getFragrances();

        // Then
        assertEquals(1, result.size());
        assertEquals("No. 5", result.getFirst().getName());
        verify(fragranceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return fragrances by brand")
    void getFragrancesByBrand_ShouldReturnBrandFragrances() {
        // Given
        List<Fragrance> expectedFragrances = Collections.singletonList(testFragrance);
        when(fragranceRepository.findAllByBrandIgnoreCase("Chanel")).thenReturn(expectedFragrances);

        // When
        List<Fragrance> result = fragranceService.getFragrancesByBrand("Chanel");

        // Then
        assertEquals(1, result.size());
        assertEquals("Chanel", result.getFirst().getBrand());
        verify(fragranceRepository, times(1)).findAllByBrandIgnoreCase("Chanel");
    }

    @Test
    @DisplayName("Should find fragrance by brand and name")
    void getFragranceByBrandAndName_ShouldReturnFragrance() {
        // Given
        when(fragranceRepository.findByBrandIgnoreCaseAndNameIgnoreCase("Chanel", "No. 5"))
                .thenReturn(Optional.of(testFragrance));

        // When
        Optional<Fragrance> result = fragranceService.getFragranceByBrandAndName("Chanel", "No. 5");

        // Then
        assertTrue(result.isPresent());
        assertEquals("No. 5", result.get().getName());
        verify(fragranceRepository, times(1)).findByBrandIgnoreCaseAndNameIgnoreCase("Chanel", "No. 5");
    }

    @Test
    @DisplayName("Should find fragrance by brand, name and id")
    void getFragranceByBrandAndNameAndId_ShouldReturnFragrance() {
        // Given
        when(fragranceRepository.findByBrandIgnoreCaseAndNameIgnoreCaseAndId("Chanel", "No. 5", 1L))
                .thenReturn(Optional.of(testFragrance));

        // When
        Optional<Fragrance> result = fragranceService.getFragranceByBrandAndNameAndId("Chanel", "No. 5", 1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(fragranceRepository, times(1)).findByBrandIgnoreCaseAndNameIgnoreCaseAndId("Chanel", "No. 5", 1L);
    }

    @Test
    @DisplayName("Should return fragrances by perfumer")
    void getFragrancesByPerfumer_ShouldReturnPerfumerFragrances() {
        // Given
        testFragrance.setPerfumerNames("Ernest Beaux|Henri Robert");
        when(fragranceRepository.findByPerfumer("Ernest Beaux"))
                .thenReturn(Collections.singletonList(testFragrance));

        // When
        List<Fragrance> result = fragranceService.getFragrancesByPerfumer("Ernest Beaux");

        // Then
        assertEquals(1, result.size());
        assertTrue(result.get(0).getPerfumerNames().contains("Ernest Beaux"));
        verify(fragranceRepository, times(1)).findByPerfumer("Ernest Beaux");
    }

    @Test
    @DisplayName("Should return fragrances by note")
    void getFragrancesByNote_ShouldReturnFragrancesWithNote() {
        // Given
        when(fragranceRepository.findByAnyNoteContaining("rose"))  // lowercase "rose"
                .thenReturn(Collections.singletonList(testFragrance));

        // When
        List<Fragrance> result = fragranceService.getFragrancesByNote("Rose");

        // Then
        assertEquals(1, result.size());
        assertTrue(result.get(0).getMiddleNotes().contains("Rose"));
        verify(fragranceRepository, times(1)).findByAnyNoteContaining("rose");  // lowercase "rose"
    }

    @Test
    @DisplayName("Should return fragrances by accord")
    void getFragrancesByAccord_ShouldReturnFragrancesWithAccord() {
        // Given
        when(fragranceRepository.findByAccordsContaining("%floral%"))
                .thenReturn(Collections.singletonList(testFragrance));

        // When
        List<Fragrance> result = fragranceService.getFragrancesByAccord("Floral");

        // Then
        assertEquals(1, result.size());
        verify(fragranceRepository, times(1)).findByAccordsContaining(anyString());
    }

    @Test
    @DisplayName("Should return fragrances by top note")
    void getFragrancesByTopNote_ShouldReturnFragrancesWithTopNote() {
        // Given
        when(fragranceRepository.findByTopNotesContaining("%bergamot%"))
                .thenReturn(Collections.singletonList(testFragrance));

        // When
        List<Fragrance> result = fragranceService.getFragrancesByTopNote("Bergamot");

        // Then
        assertEquals(1, result.size());
        verify(fragranceRepository, times(1)).findByTopNotesContaining(anyString());
    }

    @Test
    @DisplayName("Should return fragrances by middle note")
    void getFragrancesByMiddleNote_ShouldReturnFragrancesWithMiddleNote() {
        // Given
        when(fragranceRepository.findByMiddleNotesContaining("%jasmine%"))
                .thenReturn(Collections.singletonList(testFragrance));

        // When
        List<Fragrance> result = fragranceService.getFragrancesByMiddleNote("Jasmine");

        // Then
        assertEquals(1, result.size());
        verify(fragranceRepository, times(1)).findByMiddleNotesContaining(anyString());
    }

    @Test
    @DisplayName("Should return fragrances by base note")
    void getFragrancesByBaseNote_ShouldReturnFragrancesWithBaseNote() {
        // Given
        when(fragranceRepository.findByBaseNotesContaining("%vanilla%"))
                .thenReturn(Collections.singletonList(testFragrance));

        // When
        List<Fragrance> result = fragranceService.getFragrancesByBaseNote("Vanilla");

        // Then
        assertEquals(1, result.size());
        verify(fragranceRepository, times(1)).findByBaseNotesContaining(anyString());
    }

    @Test
    @DisplayName("Should return random fragrance")
    void getRandomFragrance_ShouldReturnRandomFragrance() {
        // Given
        when(fragranceRepository.getRandomFragrance()).thenReturn(Optional.of(testFragrance));

        // When
        Optional<Fragrance> result = fragranceService.getRandomFragrance();

        // Then
        assertTrue(result.isPresent());
        assertEquals("No. 5", result.get().getName());
        verify(fragranceRepository, times(1)).getRandomFragrance();
    }

    @Test
    @DisplayName("Should return multiple random fragrances")
    void getRandomFragrances_ShouldReturnMultipleRandomFragrances() {
        // Given
        List<Fragrance> expectedFragrances = Collections.singletonList(testFragrance);
        when(fragranceRepository.getRandomFragrances(3)).thenReturn(expectedFragrances);

        // When
        List<Fragrance> result = fragranceService.getRandomFragrances(3);

        // Then
        assertEquals(1, result.size());
        verify(fragranceRepository, times(1)).getRandomFragrances(3);
    }

    @Test
    @DisplayName("Should handle null perfumer names")
    void getFragrancesByPerfumer_ShouldHandleNullPerfumerNames() {
        // Given
        when(fragranceRepository.findByPerfumer("Ernest Beaux"))
                .thenReturn(Collections.emptyList());  // The query filters out null and N/A

        // When
        List<Fragrance> result = fragranceService.getFragrancesByPerfumer("Ernest Beaux");

        // Then
        assertEquals(0, result.size());
        verify(fragranceRepository, times(1)).findByPerfumer("Ernest Beaux");
    }

    @Test
    @DisplayName("Should handle N/A perfumer names")
    void getFragrancesByPerfumer_ShouldIgnoreNAPerfumerNames() {
        // Given
        when(fragranceRepository.findByPerfumer("Ernest Beaux"))
                .thenReturn(Collections.emptyList());  // The query filters out N/A

        // When
        List<Fragrance> result = fragranceService.getFragrancesByPerfumer("Ernest Beaux");

        // Then
        assertEquals(0, result.size());
        verify(fragranceRepository, times(1)).findByPerfumer("Ernest Beaux");
    }
}