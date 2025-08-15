package com.snyl.scentanyl.brand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BrandService brandService;

    @Test
    @DisplayName("GET /api/brands should return all brands")
    void getBrands_ShouldReturnAllBrands() throws Exception {
        // Given
        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Chanel");
        brand1.setCountry("France");
        brand1.setTotalFragrances(150);

        Brand brand2 = new Brand();
        brand2.setId(2L);
        brand2.setName("Dior");
        brand2.setCountry("France");
        brand2.setTotalFragrances(200);

        List<Brand> brands = Arrays.asList(brand1, brand2);
        when(brandService.getBrands()).thenReturn(brands);

        // When & Then
        mockMvc.perform(get("/api/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Chanel")))
                .andExpect(jsonPath("$[0].country", is("France")))
                .andExpect(jsonPath("$[1].name", is("Dior")));

        verify(brandService, times(1)).getBrands();
    }

    @Test
    @DisplayName("GET /api/brands/{brand}/info should return brand details")
    void getBrand_ShouldReturnBrandDetails() throws Exception {
        // Given
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Chanel");
        brand.setCountry("France");
        brand.setParent("Chanel Group");
        brand.setTotalFragrances(150);

        when(brandService.getBrandByName("Chanel")).thenReturn(Optional.of(brand));

        // When & Then
        mockMvc.perform(get("/api/brands/Chanel/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Chanel")))
                .andExpect(jsonPath("$.country", is("France")))
                .andExpect(jsonPath("$.parent", is("Chanel Group")))
                .andExpect(jsonPath("$.totalFragrances", is(150)));

        verify(brandService, times(1)).getBrandByName("Chanel");
    }

    @Test
    @DisplayName("GET /api/brands/{brand}/info should return empty when brand not found")
    void getBrand_ShouldReturnEmpty_WhenBrandNotFound() throws Exception {
        // Given
        when(brandService.getBrandByName("NonExistent")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/brands/NonExistent/info"))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));  // Changed from "" to "null"

        verify(brandService, times(1)).getBrandByName("NonExistent");
    }

    @Test
    @DisplayName("GET /api/random-brand should return single random brand")
    void getRandomBrand_WithDefaultCount_ShouldReturnSingleBrand() throws Exception {
        // Given
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Random Brand");

        when(brandService.getRandomBrand()).thenReturn(Optional.of(brand));

        // When & Then
        mockMvc.perform(get("/api/random-brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Random Brand")));

        verify(brandService, times(1)).getRandomBrand();
        verify(brandService, never()).getRandomBrands(anyInt());
    }

    @Test
    @DisplayName("GET /api/random-brand?count=3 should return multiple brands")
    void getRandomBrand_WithCount_ShouldReturnMultipleBrands() throws Exception {
        // Given
        Brand brand1 = new Brand();
        brand1.setName("Brand1");
        Brand brand2 = new Brand();
        brand2.setName("Brand2");
        Brand brand3 = new Brand();
        brand3.setName("Brand3");

        List<Brand> brands = Arrays.asList(brand1, brand2, brand3);
        when(brandService.getRandomBrands(3)).thenReturn(brands);

        // When & Then
        mockMvc.perform(get("/api/random-brand")
                        .param("count", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Brand1")))
                .andExpect(jsonPath("$[1].name", is("Brand2")))
                .andExpect(jsonPath("$[2].name", is("Brand3")));

        verify(brandService, times(1)).getRandomBrands(3);
        verify(brandService, never()).getRandomBrand();
    }

    @Test
    @DisplayName("GET /api/random-brand should return 404 when no brand found")
    void getRandomBrand_WhenNoBrandFound_ShouldReturn404() throws Exception {
        // Given
        when(brandService.getRandomBrand()).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/random-brand"))
                .andExpect(status().isNotFound());

        verify(brandService, times(1)).getRandomBrand();
    }
}