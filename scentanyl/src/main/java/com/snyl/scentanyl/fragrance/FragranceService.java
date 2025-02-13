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
        return fragranceRepository.findAllByBrand(brand);
    }
    public List<Fragrance> getFragrancesByFragranceName(String fragrance_name) {
        return fragranceRepository.findAllByFragranceName(fragrance_name);
    }
    public List<Fragrance> getFragrancesByGender(String gender) {
        return fragranceRepository.findAllByGender(gender);
    }
    public List<Fragrance> getFragrancesByPerfumer(String perfumer) {
        List<Fragrance> fragrances = new ArrayList<>();
        fragrances.addAll(fragranceRepository.findAllByPerfumer1(perfumer));
        fragrances.addAll(fragranceRepository.findAllByPerfumer2(perfumer));
        return fragrances;
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
    public List<String> getDistinctBrands(){
        return fragranceRepository.findAllDistinctBrands();
    }
    public Optional<Fragrance> getFragranceByName(String fragrance_name) {
        return fragranceRepository.findByFragranceName(fragrance_name);
    }
    public List<String> getDistinctCountries(){
        return fragranceRepository.findAllDistinctCountries();
    }
    public List<String> getDistinctPerfumers(){
        Set<String> perfumers = new HashSet<>();
        perfumers.addAll(fragranceRepository.findAllDistinctPerfumer1());
        perfumers.addAll(fragranceRepository.findAllDistinctPerfumer2());
        return List.copyOf(perfumers);
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
}
