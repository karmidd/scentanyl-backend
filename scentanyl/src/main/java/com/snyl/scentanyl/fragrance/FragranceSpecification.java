package com.snyl.scentanyl.fragrance;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragranceSpecification {
    public static Specification<Fragrance> withFilters(
            String search, String gender, Integer minYear, Integer maxYear,
            String advancedMode, String accords, String excludedAccords,
            String topNotes, String middleNotes, String baseNotes,
            String excludedTopNotes, String excludedMiddleNotes, String excludedBaseNotes,
            String notes, String excludedNotes
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Basic search
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("accords")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("topNotes")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("middleNotes")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("baseNotes")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("uncategorizedNotes")), searchPattern)
                ));
            }

            // Gender filter
            if (gender != null && !gender.equals("all")) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("gender")),
                        gender.toLowerCase()
                ));
            }

            // Year filters
            if (minYear != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("year"), minYear.longValue()));
            }
            if (maxYear != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("year"), maxYear.longValue()));
            }

            // Advanced search filters
            if (advancedMode != null && !advancedMode.equals("regular")) {
                // Accords filters
                if (accords != null && !accords.isEmpty()) {
                    List<String> accordList = Arrays.asList(accords.split(","));
                    List<Predicate> accordPredicates = new ArrayList<>();
                    for (String accord : accordList) {
                        accordPredicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("accords")),
                                "%" + accord.trim().toLowerCase() + "%"
                        ));
                    }
                    predicates.add(criteriaBuilder.and(accordPredicates.toArray(new Predicate[0])));
                }

                // Excluded accords
                if (excludedAccords != null && !excludedAccords.isEmpty()) {
                    List<String> excludedAccordList = Arrays.asList(excludedAccords.split(","));
                    for (String accord : excludedAccordList) {
                        predicates.add(criteriaBuilder.notLike(
                                criteriaBuilder.lower(root.get("accords")),
                                "%" + accord.trim().toLowerCase() + "%"
                        ));
                    }
                }

                if ("layered".equals(advancedMode)) {
                    // Top notes
                    addNotePredicates(predicates, criteriaBuilder, root, "topNotes", topNotes, excludedTopNotes);
                    // Middle notes
                    addNotePredicates(predicates, criteriaBuilder, root, "middleNotes", middleNotes, excludedMiddleNotes);
                    // Base notes
                    addNotePredicates(predicates, criteriaBuilder, root, "baseNotes", baseNotes, excludedBaseNotes);
                } else if ("uncategorized".equals(advancedMode)) {
                    // Search across ALL note fields for uncategorized mode
                    if (notes != null && !notes.isEmpty()) {
                        List<String> noteList = Arrays.asList(notes.split(","));
                        List<Predicate> notePredicates = new ArrayList<>();
                        for (String note : noteList) {
                            String pattern = "%" + note.trim().toLowerCase() + "%";
                            notePredicates.add(criteriaBuilder.or(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("topNotes")), pattern),
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("middleNotes")), pattern),
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("baseNotes")), pattern),
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("uncategorizedNotes")), pattern)
                            ));
                        }
                        predicates.add(criteriaBuilder.and(notePredicates.toArray(new Predicate[0])));
                    }

                    // Handle excluded notes across all fields
                    if (excludedNotes != null && !excludedNotes.isEmpty()) {
                        List<String> excludedNoteList = Arrays.asList(excludedNotes.split(","));
                        for (String note : excludedNoteList) {
                            String pattern = "%" + note.trim().toLowerCase() + "%";
                            predicates.add(criteriaBuilder.and(
                                    criteriaBuilder.or(
                                            criteriaBuilder.isNull(root.get("topNotes")),
                                            criteriaBuilder.notLike(criteriaBuilder.lower(root.get("topNotes")), pattern)
                                    ),
                                    criteriaBuilder.or(
                                            criteriaBuilder.isNull(root.get("middleNotes")),
                                            criteriaBuilder.notLike(criteriaBuilder.lower(root.get("middleNotes")), pattern)
                                    ),
                                    criteriaBuilder.or(
                                            criteriaBuilder.isNull(root.get("baseNotes")),
                                            criteriaBuilder.notLike(criteriaBuilder.lower(root.get("baseNotes")), pattern)
                                    ),
                                    criteriaBuilder.or(
                                            criteriaBuilder.isNull(root.get("uncategorizedNotes")),
                                            criteriaBuilder.notLike(criteriaBuilder.lower(root.get("uncategorizedNotes")), pattern)
                                    )
                            ));
                        }
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addNotePredicates(List<Predicate> predicates, jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder,
                                          jakarta.persistence.criteria.Root<Fragrance> root, String fieldName,
                                          String includeNotes, String excludeNotes) {
        // Include notes
        if (includeNotes != null && !includeNotes.isEmpty()) {
            List<String> noteList = Arrays.asList(includeNotes.split(","));
            List<Predicate> notePredicates = new ArrayList<>();
            for (String note : noteList) {
                notePredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(fieldName)),
                        "%" + note.trim().toLowerCase() + "%"
                ));
            }
            predicates.add(criteriaBuilder.and(notePredicates.toArray(new Predicate[0])));
        }

        // Exclude notes
        if (excludeNotes != null && !excludeNotes.isEmpty()) {
            List<String> excludedNoteList = Arrays.asList(excludeNotes.split(","));
            for (String note : excludedNoteList) {
                predicates.add(criteriaBuilder.notLike(
                        criteriaBuilder.lower(root.get(fieldName)),
                        "%" + note.trim().toLowerCase() + "%"
                ));
            }
        }
    }
}