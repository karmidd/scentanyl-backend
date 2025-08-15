package com.snyl.scentanyl.brand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    private Brand testBrand;

    @BeforeEach
    void setUp() {
        testBrand = new Brand();
        testBrand.setId(1L);
        testBrand.setName("Chanel");
        testBrand.setCountry("France");
        testBrand.setParent("Chanel Group");
        testBrand.setLogo("chanel-logo.png");
        testBrand.setUrl("www.chanel.com");
        testBrand.setTotalFragrances(150);
    }

    @Test
    @DisplayName("Should return all brands")
    void getBrands_ShouldReturnAllBrands() {
        // Given
        Brand brand2 = new Brand();
        brand2.setId(2L);
        brand2.setName("Dior");
        List<Brand> expectedBrands = Arrays.asList(testBrand, brand2);

        when(brandRepository.findAll()).thenReturn(expectedBrands);

        // When
        List<Brand> actualBrands = brandService.getBrands();

        // Then
        assertEquals(2, actualBrands.size());
        assertEquals("Chanel", actualBrands.getFirst().getName());
        verify(brandRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find brand by name ignoring case")
    void getBrandByName_ShouldReturnBrand_WhenBrandExists() {
        // Given
        when(brandRepository.findByNameIgnoreCase("chanel")).thenReturn(Optional.of(testBrand));

        // When
        Optional<Brand> result = brandService.getBrandByName("chanel");

        // Then
        assertTrue(result.isPresent());
        assertEquals("Chanel", result.get().getName());
        assertEquals("France", result.get().getCountry());
        verify(brandRepository, times(1)).findByNameIgnoreCase("chanel");
    }

    @Test
    @DisplayName("Should return empty when brand not found")
    void getBrandByName_ShouldReturnEmpty_WhenBrandDoesNotExist() {
        // Given
        when(brandRepository.findByNameIgnoreCase("NonExistent")).thenReturn(Optional.empty());

        // When
        Optional<Brand> result = brandService.getBrandByName("NonExistent");

        // Then
        assertFalse(result.isPresent());
        verify(brandRepository, times(1)).findByNameIgnoreCase("NonExistent");
    }

    @Test
    @DisplayName("Should return random brand")
    void getRandomBrand_ShouldReturnRandomBrand() {
        // Given
        when(brandRepository.getRandomBrand()).thenReturn(Optional.of(testBrand));

        // When
        Optional<Brand> result = brandService.getRandomBrand();

        // Then
        assertTrue(result.isPresent());
        assertEquals("Chanel", result.get().getName());
        verify(brandRepository, times(1)).getRandomBrand();
    }

    @Test
    @DisplayName("Should return multiple random brands")
    void getRandomBrands_ShouldReturnMultipleRandomBrands() {
        // Given
        Brand brand2 = new Brand();
        brand2.setName("Dior");
        List<Brand> expectedBrands = Arrays.asList(testBrand, brand2);

        when(brandRepository.getRandomBrands(2)).thenReturn(expectedBrands);

        // When
        List<Brand> result = brandService.getRandomBrands(2);

        // Then
        assertEquals(2, result.size());
        verify(brandRepository, times(1)).getRandomBrands(2);
    }
}