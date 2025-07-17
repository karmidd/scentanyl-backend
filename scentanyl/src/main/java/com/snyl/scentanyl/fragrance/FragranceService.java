package com.snyl.scentanyl.fragrance;

import com.snyl.scentanyl.accord.Accord;
import com.snyl.scentanyl.accord.AccordRepository;
import com.snyl.scentanyl.brand.Brand;
import com.snyl.scentanyl.brand.BrandRepository;
import com.snyl.scentanyl.note.Note;
import com.snyl.scentanyl.note.NoteRepository;
import com.snyl.scentanyl.perfumer.Perfumer;
import com.snyl.scentanyl.perfumer.PerfumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FragranceService {
    private final FragranceRepository fragranceRepository;
    private final BrandRepository brandRepository;
    private final NoteRepository noteRepository;
    private final AccordRepository accordRepository;
    private final PerfumerRepository perfumerRepository;

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandByName(String name) {
        return brandRepository.findByNameIgnoreCase(name);
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteByName(String name) {
        return noteRepository.findByNameIgnoreCase(name);
    }

    public List<Accord> getAccords() {
        return accordRepository.findAll();
    }

    public Optional<Accord> getAccordByName(String name) {
        return accordRepository.findByNameIgnoreCase(name);
    }

    public List<Perfumer> getPerfumers() {
        return perfumerRepository.findAll();
    }

    public Optional<Perfumer> getPerfumerByName(String name) {
        return perfumerRepository.findByNameIgnoreCase(name);
    }

    public List<Fragrance> getFragrances() {
        return fragranceRepository.findAll();
    }

    public List<String> getDistinctBrands(){
        return fragranceRepository.findAllDistinctBrands();
    }

    public List<Fragrance> getFragrancesByBrand(String brand){
        return fragranceRepository.findAllByBrandIgnoreCase(brand);
    }

    public Optional<Fragrance> getFragranceByName(String name) {
        return fragranceRepository.findByName(name);
    }

    public Optional<Fragrance> getFragranceByBrandAndName(String brand, String name) {
        return fragranceRepository.findByBrandIgnoreCaseAndNameIgnoreCase(brand, name);
    }

    public Set<String> getDistinctPerfumers(){
        List<String> rawPerfumers = fragranceRepository.findAllDistinctPerfumers();

        return rawPerfumers.stream()
                .filter(p -> p != null && !p.equalsIgnoreCase("N/A"))
                .flatMap(p -> Arrays.stream(p.split("\\|")))
                .map(String::trim)
                .filter(p -> !p.isEmpty())
                .collect(Collectors.toSet());
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

    public Set<String> getDistinctAccords() {
        List<String> rawAccords = fragranceRepository.findAllDistinctAccords();

        return rawAccords.stream()
                .filter(a -> a != null && !a.equalsIgnoreCase("N/A"))
                .flatMap(a -> Arrays.stream(a.split(",")))
                .map(String::trim)
                .filter(a -> !a.isEmpty())
                .collect(Collectors.toSet());
    }

    public Set<String> getDistinctNotes() {
        Set<String> result = new HashSet<>();

        List<String> allRawNotes = new ArrayList<>();
        allRawNotes.addAll(fragranceRepository.findAllDistinctUncategorizedNotes());
        allRawNotes.addAll(fragranceRepository.findAllDistinctTopNotes());
        allRawNotes.addAll(fragranceRepository.findAllDistinctMiddleNotes());
        allRawNotes.addAll(fragranceRepository.findAllDistinctBaseNotes());

        for (String notesField : allRawNotes) {
            if (notesField != null && !notesField.equalsIgnoreCase("N/A")) {
                String[] splitNotes = notesField.split(",");
                for (String note : splitNotes) {
                    String cleaned = note.trim();
                    if (!cleaned.isEmpty()) {
                        result.add(cleaned);
                    }
                }
            }
        }

        return result;
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


    /*
    public List<Fragrance> getFragrancesByNames(String name) {
        return fragranceRepository.findAllByName(name);
    }
    public List<Fragrance> getFragrancesByGender(String gender) {
        return fragranceRepository.findAllByGender(gender);
    }

    public List<Fragrance> getFragrancesByTopNotes(String topNotes) {
        return fragranceRepository.findAllByTopNotes(topNotes);
    }
    public List<Fragrance> getFragrancesByMiddleNotes(String middleNotes) {
        return fragranceRepository.findAllByMiddleNotes(middleNotes);
    }
    public List<Fragrance> getFragrancesByBaseNotes(String baseNotes) {
        return fragranceRepository.findAllByBaseNotes(baseNotes);
    }
    public List<Fragrance> getFragrancesByMainAccord(String mainAccord) {
        List<Fragrance> fragrances = new ArrayList<>();
        fragrances.addAll(fragranceRepository.findAllByMainaccord1(mainAccord));
        fragrances.addAll(fragranceRepository.findAllByMainaccord2(mainAccord));
        fragrances.addAll(fragranceRepository.findAllByMainaccord3(mainAccord));
        fragrances.addAll(fragranceRepository.findAllByMainaccord4(mainAccord));
        fragrances.addAll(fragranceRepository.findAllByMainaccord5(mainAccord));
        return fragrances;
    }

    public List<Fragrance> getFragrancesByCountry(String country) {
        return fragranceRepository.findAllByCountry(country);
    }
    public List<Fragrance> getFragrancesByYear(Integer year) {
        return fragranceRepository.findAllByYear(year);
    }


    public List<String> getDistinctCountries(){
        return fragranceRepository.findAllDistinctCountries();
    }


    public List<String> getDistinctNotes(){
        Set<String> notes = new HashSet<>();
        notes.addAll(fragranceRepository.findAllDistinctTopNotes());
        notes.addAll(fragranceRepository.findAllDistinctMiddleNotes());
        notes.addAll(fragranceRepository.findAllDistinctBaseNotes());
        notes = notes.stream()
                .flatMap(s -> Arrays.stream(s.split(", ")))
                .collect(Collectors.toSet());
        return List.copyOf(notes);
    }
    public List<String> getDistinctMainAccords(){
        Set<String> mainAccords = new HashSet<>();
        mainAccords.addAll(fragranceRepository.findAllDistinctMainAccord1());
        mainAccords.addAll(fragranceRepository.findAllDistinctMainAccord2());
        mainAccords.addAll(fragranceRepository.findAllDistinctMainAccord3());
        mainAccords.addAll(fragranceRepository.findAllDistinctMainAccord4());
        mainAccords.addAll(fragranceRepository.findAllDistinctMainAccord5());
        return List.copyOf(mainAccords);
    }
    */
}
