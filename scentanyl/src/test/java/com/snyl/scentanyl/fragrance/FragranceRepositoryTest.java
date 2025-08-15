package com.snyl.scentanyl.fragrance;

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
class FragranceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FragranceRepository fragranceRepository;

    private Fragrance testFragrance1;
    private Fragrance testFragrance2;

    @BeforeEach
    void setUp() {
        // Clear the database
        fragranceRepository.deleteAll();
        
        // Create test data
        testFragrance1 = new Fragrance();
        testFragrance1.setName("No. 5");
        testFragrance1.setBrand("Chanel");
        testFragrance1.setGender("Women");
        testFragrance1.setYear(1921L);
        testFragrance1.setPerfumerNames("Ernest Beaux");
        testFragrance1.setAccords("Aldehyde, Floral, Powdery");
        testFragrance1.setTopNotes("Aldehyde, Bergamot, Lemon");
        testFragrance1.setMiddleNotes("Rose, Jasmine, Ylang-Ylang");
        testFragrance1.setBaseNotes("Sandalwood, Vetiver, Vanilla");
        testFragrance1.setImageUrl("no5.jpg");

        testFragrance2 = new Fragrance();
        testFragrance2.setName("Sauvage");
        testFragrance2.setBrand("Dior");
        testFragrance2.setGender("Men");
        testFragrance2.setYear(2015L);
        testFragrance2.setPerfumerNames("François Demachy");
        testFragrance2.setAccords("Fresh, Spicy, Woody");
        testFragrance2.setTopNotes("Bergamot, Pepper");
        testFragrance2.setMiddleNotes("Geranium, Lavender");
        testFragrance2.setBaseNotes("Cedar, Patchouli");
        testFragrance2.setImageUrl("sauvage.jpg");

        // Persist test data
        entityManager.persist(testFragrance1);
        entityManager.persist(testFragrance2);
        entityManager.flush();
    }

    @Test
    @DisplayName("Should find fragrance by brand and name ignoring case")
    void findByBrandIgnoreCaseAndNameIgnoreCase_ShouldReturnFragrance() {
        // When
        Optional<Fragrance> found = fragranceRepository
                .findByBrandIgnoreCaseAndNameIgnoreCase("CHANEL", "no. 5");

        // Then
        assertTrue(found.isPresent());
        assertEquals("No. 5", found.get().getName());
        assertEquals("Chanel", found.get().getBrand());
    }

    @Test
    @DisplayName("Should find fragrance by brand, name and id")
    void findByBrandIgnoreCaseAndNameIgnoreCaseAndId_ShouldReturnFragrance() {
        // Given
        Long id = testFragrance1.getId();

        // When
        Optional<Fragrance> found = fragranceRepository
                .findByBrandIgnoreCaseAndNameIgnoreCaseAndId("chanel", "No. 5", id);

        // Then
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
    }

    @Test
    @DisplayName("Should find all fragrances by brand")
    void findAllByBrandIgnoreCase_ShouldReturnBrandFragrances() {
        // Given
        Fragrance anotherChanel = new Fragrance();
        anotherChanel.setName("Coco");
        anotherChanel.setBrand("Chanel");
        entityManager.persist(anotherChanel);
        entityManager.flush();

        // When
        List<Fragrance> chanelFragrances = fragranceRepository.findAllByBrandIgnoreCase("chanel");

        // Then
        assertEquals(2, chanelFragrances.size());
        assertTrue(chanelFragrances.stream().allMatch(f -> f.getBrand().equalsIgnoreCase("Chanel")));
    }

    @Test
    @DisplayName("Should find fragrances by top notes containing")
    void findByTopNotesContaining_ShouldReturnMatchingFragrances() {
        // When
        List<Fragrance> fragrances = fragranceRepository.findByTopNotesContaining("bergamot");

        // Then
        assertEquals(2, fragrances.size()); // Both have Bergamot
    }

    @Test
    @DisplayName("Should find fragrances by middle notes containing")
    void findByMiddleNotesContaining_ShouldReturnMatchingFragrances() {
        // When
        List<Fragrance> fragrances = fragranceRepository.findByMiddleNotesContaining("rose");

        // Then
        assertEquals(1, fragrances.size());
        assertEquals("No. 5", fragrances.getFirst().getName());
    }

    @Test
    @DisplayName("Should find fragrances by base notes containing")
    void findByBaseNotesContaining_ShouldReturnMatchingFragrances() {
        // When
        List<Fragrance> fragrances = fragranceRepository.findByBaseNotesContaining("vanilla");

        // Then
        assertEquals(1, fragrances.size());
        assertEquals("No. 5", fragrances.getFirst().getName());
    }

    @Test
    @DisplayName("Should find fragrances by accords containing")
    void findByAccordsContaining_ShouldReturnMatchingFragrances() {
        // When
        List<Fragrance> fragrances = fragranceRepository.findByAccordsContaining("floral");

        // Then
        assertEquals(1, fragrances.size());
        assertEquals("No. 5", fragrances.getFirst().getName());
    }

    @Test
    @DisplayName("Should return random fragrance")
    void getRandomFragrance_ShouldReturnRandomFragrance() {
        // When
        Optional<Fragrance> randomFragrance = fragranceRepository.getRandomFragrance();

        // Then
        assertTrue(randomFragrance.isPresent());
        assertTrue(randomFragrance.get().getName().equals("No. 5") || 
                  randomFragrance.get().getName().equals("Sauvage"));
    }

    @Test
    @DisplayName("Should return multiple random fragrances")
    void getRandomFragrances_ShouldReturnRequestedNumber() {
        // When
        List<Fragrance> randomFragrances = fragranceRepository.getRandomFragrances(2);

        // Then
        assertEquals(2, randomFragrances.size());
    }

    @Test
    @DisplayName("Should handle case-insensitive search in accords")
    void findByAccordsContaining_ShouldBeCaseInsensitive() {
        // When
        List<Fragrance> upperCase = fragranceRepository.findByAccordsContaining("WOODY");
        List<Fragrance> lowerCase = fragranceRepository.findByAccordsContaining("woody");
        List<Fragrance> mixedCase = fragranceRepository.findByAccordsContaining("WoOdY");

        // Then - all should find the same fragrance
        assertEquals(1, upperCase.size());
        assertEquals(1, lowerCase.size());
        assertEquals(1, mixedCase.size());
        assertEquals("Sauvage", upperCase.getFirst().getName());
    }

    @Test
    @DisplayName("Should save and retrieve fragrance with all fields")
    void save_ShouldPersistAllFragranceFields() {
        // Given
        Fragrance newFragrance = new Fragrance();
        newFragrance.setName("Aventus");
        newFragrance.setBrand("Creed");
        newFragrance.setGender("Men");
        newFragrance.setYear(2010L);
        newFragrance.setPerfumerNames("Olivier Creed|Erwin Creed");
        newFragrance.setPerfumerImages("olivier.jpg|erwin.jpg");
        newFragrance.setAccords("Fruity, Woody");
        newFragrance.setTopNotes("Pineapple, Blackcurrant");
        newFragrance.setMiddleNotes("Birch, Patchouli");
        newFragrance.setBaseNotes("Musk, Oak Moss");
        newFragrance.setUncategorizedNotes("Ambergris");
        newFragrance.setImageUrl("aventus.jpg");

        // When
        Fragrance saved = fragranceRepository.save(newFragrance);
        Optional<Fragrance> retrieved = fragranceRepository.findById(saved.getId());

        // Then
        assertTrue(retrieved.isPresent());
        Fragrance f = retrieved.get();
        assertEquals("Aventus", f.getName());
        assertEquals("Creed", f.getBrand());
        assertEquals("Men", f.getGender());
        assertEquals(2010L, f.getYear());
        assertEquals("Olivier Creed|Erwin Creed", f.getPerfumerNames());
        assertEquals("olivier.jpg|erwin.jpg", f.getPerfumerImages());
        assertEquals("Fruity, Woody", f.getAccords());
        assertEquals("Pineapple, Blackcurrant", f.getTopNotes());
        assertEquals("Birch, Patchouli", f.getMiddleNotes());
        assertEquals("Musk, Oak Moss", f.getBaseNotes());
        assertEquals("Ambergris", f.getUncategorizedNotes());
        assertEquals("aventus.jpg", f.getImageUrl());
    }
}