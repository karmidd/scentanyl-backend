package com.snyl.scentanyl.accord;

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
class AccordServiceTest {

    @Mock
    private AccordRepository accordRepository;

    @InjectMocks
    private AccordService accordService;

    private Accord testAccord1;
    private Accord testAccord2;

    @BeforeEach
    void setUp() {
        testAccord1 = new Accord();
        // Don't set ID - it's auto-generated
        testAccord1.setName("Floral");
        testAccord1.setTotalFragrances(500);

        testAccord2 = new Accord();
        // Don't set ID - it's auto-generated
        testAccord2.setName("Woody");
        testAccord2.setTotalFragrances(350);
    }

    @Test
    @DisplayName("Should return all accords")
    void getAccords_ShouldReturnAllAccords() {
        // Given
        List<Accord> expectedAccords = Arrays.asList(testAccord1, testAccord2);
        when(accordRepository.findAll()).thenReturn(expectedAccords);

        // When
        List<Accord> result = accordService.getAccords();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Floral", result.get(0).getName());
        assertEquals("Woody", result.get(1).getName());
        assertEquals(500, result.get(0).getTotalFragrances());
        assertEquals(350, result.get(1).getTotalFragrances());
        verify(accordRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no accords exist")
    void getAccords_ShouldReturnEmptyList_WhenNoAccordsExist() {
        // Given
        when(accordRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Accord> result = accordService.getAccords();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(accordRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return single accord")
    void getAccords_ShouldReturnSingleAccord() {
        // Given
        List<Accord> expectedAccords = Collections.singletonList(testAccord1);
        when(accordRepository.findAll()).thenReturn(expectedAccords);

        // When
        List<Accord> result = accordService.getAccords();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Floral", result.get(0).getName());
        verify(accordRepository, times(1)).findAll();
    }
}