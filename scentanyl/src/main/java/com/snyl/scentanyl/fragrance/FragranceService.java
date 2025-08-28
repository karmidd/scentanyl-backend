package com.snyl.scentanyl.fragrance;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FragranceService {
    private final FragranceRepository fragranceRepository;

    public List<Fragrance> getFragrances() {
        return fragranceRepository.findAll();
    }

    public List<Fragrance> getFragrancesByBrand(String brand){
        return fragranceRepository.findAllByBrandIgnoreCase(brand);
    }

    public Optional<Fragrance> getFragranceByBrandAndName(String brand, String name) {
        return fragranceRepository.findByBrandIgnoreCaseAndNameIgnoreCase(brand, name);
    }

    public Optional<Fragrance> getFragranceByBrandAndNameAndId(String brand, String name, Long id) {
        return fragranceRepository.findByBrandIgnoreCaseAndNameIgnoreCaseAndId(brand, name, id);
    }

    public List<Fragrance> getFragrancesByPerfumer(String perfumerName) {
        return fragranceRepository.findByPerfumer(perfumerName.trim());
    }

    public List<Fragrance> getFragrancesByNote(String note) {
        return fragranceRepository.findByAnyNoteContaining(note.trim().toLowerCase());
    }

    private boolean hasNote(Fragrance fragrance, String noteLower) {
        return containsNote(fragrance.getUncategorizedNotes(), noteLower)
                || containsNote(fragrance.getTopNotes(), noteLower)
                || containsNote(fragrance.getMiddleNotes(), noteLower)
                || containsNote(fragrance.getBaseNotes(), noteLower);
    }

    private boolean containsNote(String notesField, String noteLower) {
        if (notesField == null || notesField.equalsIgnoreCase("N/A")) {
            return false;
        }
        return Arrays.stream(notesField.split(","))
                .map(String::trim)
                .anyMatch(n -> n.equalsIgnoreCase(noteLower));
    }

    public List<Fragrance> getFragrancesByAccord(String accord) {
        String likeTerm = "%" + accord.toLowerCase() + "%";
        List<Fragrance> roughMatches = fragranceRepository.findByAccordsContaining(likeTerm);

        String target = accord.trim().toLowerCase();

        return roughMatches.stream()
                .filter(f -> hasExactAccord(f, target))
                .collect(Collectors.toList());
    }

    public Page<Fragrance> getFragrancesByAccordPaginated(
            String accord, int page, int size, String search, String gender,
            Integer minYear, Integer maxYear, String sortBy, String sortDirection,
            String advancedMode, String accords, String excludedAccords,
            String topNotes, String middleNotes, String baseNotes,
            String excludedTopNotes, String excludedMiddleNotes, String excludedBaseNotes,
            String notes, String excludedNotes) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDirection != null ? sortDirection : "ASC"),
                        sortBy != null ? sortBy : "name"));

        // Create base specification for accord
        Specification<Fragrance> accordSpec = (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("accords")),
                        "%" + accord.trim().toLowerCase() + "%"
                );

        // Add other filters
        Specification<Fragrance> filterSpec = FragranceSpecification.withFilters(
                search, gender, minYear, maxYear, advancedMode,
                accords, excludedAccords, topNotes, middleNotes, baseNotes,
                excludedTopNotes, excludedMiddleNotes, excludedBaseNotes,
                notes, excludedNotes
        );

        // Combine specifications
        Specification<Fragrance> combinedSpec = accordSpec.and(filterSpec);

        return fragranceRepository.findAll(combinedSpec, pageable);
    }

    private boolean hasExactAccord(Fragrance f, String target) {
        if (f.getAccords() == null) return false;

        String[] tokens = f.getAccords().toLowerCase().split(",");
        for (String token : tokens) {
            if (token.trim().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public List<Fragrance> getFragrancesByTopNote(String note) {
        String likeTerm = "%" + note.toLowerCase() + "%";
        List<Fragrance> roughMatches = fragranceRepository.findByTopNotesContaining(likeTerm);

        String target = note.trim().toLowerCase();

        return roughMatches.stream()
                .filter(f -> matchesNoteList(f.getTopNotes(), target))
                .collect(Collectors.toList());
    }

    public List<Fragrance> getFragrancesByMiddleNote(String note) {
        String likeTerm = "%" + note.toLowerCase() + "%";
        List<Fragrance> roughMatches = fragranceRepository.findByMiddleNotesContaining(likeTerm);

        String target = note.trim().toLowerCase();

        return roughMatches.stream()
                .filter(f -> matchesNoteList(f.getMiddleNotes(), target))
                .collect(Collectors.toList());
    }

    public List<Fragrance> getFragrancesByBaseNote(String note) {
        String likeTerm = "%" + note.toLowerCase() + "%";
        List<Fragrance> roughMatches = fragranceRepository.findByBaseNotesContaining(likeTerm);

        String target = note.trim().toLowerCase();

        return roughMatches.stream()
                .filter(f -> matchesNoteList(f.getBaseNotes(), target))
                .collect(Collectors.toList());
    }

    private boolean matchesNoteList(String notesString, String target) {
        if (notesString == null) return false;

        String[] tokens = notesString.toLowerCase().split(",");
        for (String token : tokens) {
            if (token.trim().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Fragrance> getRandomFragrance() {
        return fragranceRepository.getRandomFragrance();
    }

    public List<Fragrance> getRandomFragrances(int count) {
        return fragranceRepository.getRandomFragrances(count);
    }

    public Page<Fragrance> getFragrancesFiltered(
            int page, int size, String search, String gender,
            Integer minYear, Integer maxYear, String sortBy, String sortDirection,
            String advancedMode, String accords, String excludedAccords,
            String topNotes, String middleNotes, String baseNotes,
            String excludedTopNotes, String excludedMiddleNotes, String excludedBaseNotes,
            String notes, String excludedNotes) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDirection != null ? sortDirection : "ASC"),
                        sortBy != null ? sortBy : "name"));

        Specification<Fragrance> spec = FragranceSpecification.withFilters(
                search, gender, minYear, maxYear, advancedMode,
                accords, excludedAccords, topNotes, middleNotes, baseNotes,
                excludedTopNotes, excludedMiddleNotes, excludedBaseNotes,
                notes, excludedNotes
        );

        return fragranceRepository.findAll(spec, pageable);
    }

    public Map<String, Object> getFragranceStats() {
        Map<String, Object> stats = new HashMap<>();

        // Get min/max years
        stats.put("minYear", fragranceRepository.findMinYear());
        stats.put("maxYear", fragranceRepository.findMaxYear());

        // Get gender counts
        Map<String, Long> genderCounts = new HashMap<>();
        genderCounts.put("all", fragranceRepository.count());
        genderCounts.put("men", fragranceRepository.countByGenderIgnoreCase("men"));
        genderCounts.put("women", fragranceRepository.countByGenderIgnoreCase("women"));
        genderCounts.put("unisex", fragranceRepository.countByGenderIgnoreCase("unisex"));
        stats.put("genderCounts", genderCounts);

        return stats;
    }

    public Map<String, Object> getAccordStats(String accord) {
        Map<String, Object> stats = new HashMap<>();

        // Get min/max years
        stats.put("minYear", fragranceRepository.findMinYearByAccord(accord));
        stats.put("maxYear", fragranceRepository.findMaxYearByAccord(accord));

        // Get gender counts
        Map<String, Long> genderCounts = new HashMap<>();
        genderCounts.put("all", fragranceRepository.countByAccord(accord));
        genderCounts.put("men", fragranceRepository.countByAccordAndGender(accord, "men"));
        genderCounts.put("women", fragranceRepository.countByAccordAndGender(accord, "women"));
        genderCounts.put("unisex", fragranceRepository.countByAccordAndGender(accord, "unisex"));
        stats.put("genderCounts", genderCounts);

        return stats;
    }
    public Page<Fragrance> getFragrancesByNotePaginated(
            String note, String position, int page, int size, String search, String gender,
            Integer minYear, Integer maxYear, String sortBy, String sortDirection,
            String advancedMode, String accords, String excludedAccords,
            String topNotes, String middleNotes, String baseNotes,
            String excludedTopNotes, String excludedMiddleNotes, String excludedBaseNotes,
            String notes, String excludedNotes) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDirection != null ? sortDirection : "ASC"),
                        sortBy != null ? sortBy : "name"));

        // Create base specification for note based on position
        Specification<Fragrance> noteSpec = (root, query, criteriaBuilder) -> {
            String pattern = "%" + note.trim().toLowerCase() + "%";

            if (position == null || "all".equals(position)) {
                // Search in all positions
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("topNotes")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("middleNotes")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("baseNotes")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("uncategorizedNotes")), pattern)
                );
            } else if ("top".equals(position)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("topNotes")), pattern);
            } else if ("middle".equals(position)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("middleNotes")), pattern);
            } else if ("base".equals(position)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("baseNotes")), pattern);
            } else if ("uncategorized".equals(position)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("uncategorizedNotes")), pattern);
            }
            return criteriaBuilder.conjunction(); // Return always true if position is invalid
        };

        // Add other filters
        Specification<Fragrance> filterSpec = FragranceSpecification.withFilters(
                search, gender, minYear, maxYear, advancedMode,
                accords, excludedAccords, topNotes, middleNotes, baseNotes,
                excludedTopNotes, excludedMiddleNotes, excludedBaseNotes,
                notes, excludedNotes
        );

        // Combine specifications
        Specification<Fragrance> combinedSpec = noteSpec.and(filterSpec);

        return fragranceRepository.findAll(combinedSpec, pageable);
    }

    public Map<String, Object> getNoteStats(String note) {
        Map<String, Object> stats = new HashMap<>();

        String noteLower = note.trim().toLowerCase();

        // Get counts for each position
        long topCount = fragranceRepository.countByTopNoteContaining(noteLower);
        long middleCount = fragranceRepository.countByMiddleNoteContaining(noteLower);
        long baseCount = fragranceRepository.countByBaseNoteContaining(noteLower);
        long uncategorizedCount = fragranceRepository.countByUncategorizedNoteContaining(noteLower);

        stats.put("topNotes", topCount);
        stats.put("middleNotes", middleCount);
        stats.put("baseNotes", baseCount);
        stats.put("uncategorizedNotes", uncategorizedCount);
        stats.put("totalFragrances", topCount + middleCount + baseCount + uncategorizedCount);

        // Get min/max years
        stats.put("minYear", fragranceRepository.findMinYearByNote(note));
        stats.put("maxYear", fragranceRepository.findMaxYearByNote(note));

        return stats;
    }
}
