package com.snyl.scentanyl.brand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BrandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        // Clear the database
        brandRepository.deleteAll();
        
        // Create test data
        Brand testBrand1 = new Brand();
        testBrand1.setName("Chanel");
        testBrand1.setCountry("France");
        testBrand1.setParent("Chanel Group");
        testBrand1.setLogo("chanel-logo.png");
        testBrand1.setUrl("www.chanel.com");
        testBrand1.setTotalFragrances(150);

        Brand testBrand2 = new Brand();
        testBrand2.setName("Dior");
        testBrand2.setCountry("France");
        testBrand2.setParent("LVMH");
        testBrand2.setLogo("dior-logo.png");
        testBrand2.setUrl("www.dior.com");
        testBrand2.setTotalFragrances(200);
        
        // Persist test data
        entityManager.persist(testBrand1);
        entityManager.persist(testBrand2);
        entityManager.flush();
    }

    @Test
    @DisplayName("Should find brand by name ignoring case")
    void findByNameIgnoreCase_ShouldReturnBrand() {
        // When
        Optional<Brand> foundBrand = brandRepository.findByNameIgnoreCase("CHANEL");

        // Then
        assertTrue(foundBrand.isPresent());
        assertEquals("Chanel", foundBrand.get().getName());
        assertEquals("France", foundBrand.get().getCountry());
    }

    @Test
    @DisplayName("Should return empty when brand name not found")
    void findByNameIgnoreCase_ShouldReturnEmpty_WhenNotFound() {
        // When
        Optional<Brand> foundBrand = brandRepository.findByNameIgnoreCase("NonExistent");

        // Then
        assertFalse(foundBrand.isPresent());
    }

    @Test
    @DisplayName("Should find brand by name with different case")
    void findByNameIgnoreCase_ShouldWorkWithDifferentCases() {
        // When
        Optional<Brand> lowercase = brandRepository.findByNameIgnoreCase("dior");
        Optional<Brand> uppercase = brandRepository.findByNameIgnoreCase("DIOR");
        Optional<Brand> mixedCase = brandRepository.findByNameIgnoreCase("DiOr");

        // Then
        assertTrue(lowercase.isPresent());
        assertTrue(uppercase.isPresent());
        assertTrue(mixedCase.isPresent());
        assertEquals("Dior", lowercase.get().getName());
        assertEquals("Dior", uppercase.get().getName());
        assertEquals("Dior", mixedCase.get().getName());
    }

    @Test
    @DisplayName("Should return random brand")
    void getRandomBrand_ShouldReturnRandomBrand() {
        // When
        Optional<Brand> randomBrand = brandRepository.getRandomBrand();

        // Then
        assertTrue(randomBrand.isPresent());
        assertTrue(randomBrand.get().getName().equals("Chanel") || 
                  randomBrand.get().getName().equals("Dior"));
    }

    @Test
    @DisplayName("Should return multiple random brands")
    void getRandomBrands_ShouldReturnRequestedNumberOfBrands() {
        // When
        List<Brand> randomBrands = brandRepository.getRandomBrands(2);

        // Then
        assertEquals(2, randomBrands.size());
    }

    @Test
    @DisplayName("Should return fewer brands when count exceeds total")
    void getRandomBrands_ShouldReturnAvailableBrands_WhenCountExceedsTotal() {
        // When
        List<Brand> randomBrands = brandRepository.getRandomBrands(10);

        // Then
        assertEquals(2, randomBrands.size()); // Only 2 brands in database
    }

    @Test
    @DisplayName("Should save and retrieve brand with all fields")
    void save_ShouldPersistAllBrandFields() {
        // Given
        Brand newBrand = new Brand();
        newBrand.setName("Tom Ford");
        newBrand.setCountry("USA");
        newBrand.setParent("Estée Lauder");
        newBrand.setLogo("tomford-logo.png");
        newBrand.setUrl("www.tomford.com");
        newBrand.setTotalFragrances(50);

        // When
        Brand savedBrand = brandRepository.save(newBrand);
        Optional<Brand> retrievedBrand = brandRepository.findById(savedBrand.getId());

        // Then
        assertTrue(retrievedBrand.isPresent());
        assertEquals("Tom Ford", retrievedBrand.get().getName());
        assertEquals("USA", retrievedBrand.get().getCountry());
        assertEquals("Estée Lauder", retrievedBrand.get().getParent());
        assertEquals("tomford-logo.png", retrievedBrand.get().getLogo());
        assertEquals("www.tomford.com", retrievedBrand.get().getUrl());
        assertEquals(50, retrievedBrand.get().getTotalFragrances());
    }

    @Test
    @DisplayName("Should find all brands")
    void findAll_ShouldReturnAllBrands() {
        // When
        List<Brand> allBrands = brandRepository.findAll();

        // Then
        assertEquals(2, allBrands.size());
        assertTrue(allBrands.stream().anyMatch(b -> b.getName().equals("Chanel")));
        assertTrue(allBrands.stream().anyMatch(b -> b.getName().equals("Dior")));
    }
}