package com.snyl.scentanyl.fragrance;

import lombok.RequiredArgsConstructor;
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

    public List<Fragrance> getFragrancesByPerfumer(String perfumerNames) {
        List<Fragrance> all = fragranceRepository.findAll();
        return all.stream()
                .filter(f -> f.getPerfumerNames() != null && !f.getPerfumerNames().equalsIgnoreCase("N/A"))
                .filter(f -> Arrays.stream(f.getPerfumerNames().split("\\|"))
                        .map(String::trim)
                        .anyMatch(p -> p.equalsIgnoreCase(perfumerNames)))
                .collect(Collectors.toList());
    }

    public List<Fragrance> getFragrancesByNote(String note) {
        String noteLower = note.trim().toLowerCase();

        return fragranceRepository.findAll().stream()
                .filter(f -> hasNote(f, noteLower))
                .collect(Collectors.toList());
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

}
