package com.snyl.scentanyl.perfumer;

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
class PerfumerServiceTest {

    @Mock
    private PerfumerRepository perfumerRepository;

    @InjectMocks
    private PerfumerService perfumerService;

    private Perfumer testPerfumer1;
    private Perfumer testPerfumer2;

    @BeforeEach
    void setUp() {
        testPerfumer1 = new Perfumer();
        // Don't set ID - it's auto-generated
        testPerfumer1.setName("Ernest Beaux");
        testPerfumer1.setImage("ernest-beaux.jpg");
        testPerfumer1.setTotalFragrances(25);

        testPerfumer2 = new Perfumer();
        // Don't set ID - it's auto-generated
        testPerfumer2.setName("Jacques Polge");
        testPerfumer2.setImage("jacques-polge.jpg");
        testPerfumer2.setTotalFragrances(75);
    }

    @Test
    @DisplayName("Should return all perfumers")
    void getPerfumers_ShouldReturnAllPerfumers() {
        // Given
        List<Perfumer> expectedPerfumers = Arrays.asList(testPerfumer1, testPerfumer2);
        when(perfumerRepository.findAll()).thenReturn(expectedPerfumers);

        // When
        List<Perfumer> result = perfumerService.getPerfumers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ernest Beaux", result.get(0).getName());
        assertEquals("Jacques Polge", result.get(1).getName());
        assertEquals("ernest-beaux.jpg", result.get(0).getImage());
        assertEquals(25, result.get(0).getTotalFragrances());
        assertEquals(75, result.get(1).getTotalFragrances());
        verify(perfumerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no perfumers exist")
    void getPerfumers_ShouldReturnEmptyList_WhenNoPerfumersExist() {
        // Given
        when(perfumerRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Perfumer> result = perfumerService.getPerfumers();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(perfumerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return single perfumer")
    void getPerfumers_ShouldReturnSinglePerfumer() {
        // Given
        List<Perfumer> expectedPerfumers = Collections.singletonList(testPerfumer1);
        when(perfumerRepository.findAll()).thenReturn(expectedPerfumers);

        // When
        List<Perfumer> result = perfumerService.getPerfumers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ernest Beaux", result.getFirst().getName());
        assertNotNull(result.getFirst().getImage());
        verify(perfumerRepository, times(1)).findAll();
    }
}