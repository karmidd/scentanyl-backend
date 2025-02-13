package com.snyl.scentanyl.fragrance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
}
