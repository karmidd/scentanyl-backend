package com.snyl.scentanyl.fragrance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class FragranceController {

    @Autowired
    private FragranceService fragranceService;

    @GetMapping({"/fragrances", "/fragrances/"})
    public List<Fragrance> getFragrances() {
        return fragranceService.getFragrances();
    }

    @GetMapping({"/brands", "/brands/"})
    public List<String> getDistinctBrands() {
        return fragranceService.getDistinctBrands();
    }

    @GetMapping({"/brands/{brand}", "/brands/{brand}/"})
    public List<Fragrance> getFragrancesByBrand(@PathVariable String brand) {
        return fragranceService.getFragrancesByBrand(brand);
    }


    @GetMapping({"/brands/{brand}/{name}", "/brands/{brand}/{name}/"})
    public Optional<Fragrance> getFragrance(@PathVariable String brand, @PathVariable String name) {
        return fragranceService.getFragranceByBrandAndName(brand, name);
    }

    @GetMapping({"/perfumers", "/perfumers/"})
    public Set<String> getDistinctPerfumers() {
        return fragranceService.getDistinctPerfumers();
    }

    @GetMapping({"/accords", "/accords/"})
    public Set<String> getDistinctAccords() {
        return fragranceService.getDistinctAccords();
    }

    @GetMapping({"/accords/{accord}", "/notes/{accord}/"})
    public List<Fragrance> getFragrancesByAccord(@PathVariable String accord) {
        return fragranceService.getFragrancesByAccord(accord);
    }

    @GetMapping({"/notes", "/notes/"})
    public Set<String> getDistinctNotes() {
        return fragranceService.getDistinctNotes();
    }

    @GetMapping({"/perfumers/{perfumerNames}", "/perfumers/{perfumerNames}/"})
    public List<Fragrance> getFragrancesByPerfumers(@PathVariable String perfumerNames) {
        return fragranceService.getFragrancesByPerfumer(perfumerNames);
    }

    @GetMapping({"/notes/{note}", "/notes/{note}/"})
    public List<Fragrance> getFragrancesByNote(@PathVariable String note) {
        return fragranceService.getFragrancesByNote(note);
    }

    @GetMapping({"/notes/top/{note}", "/notes/top/{note}/"})
    public List<Fragrance> getFragrancesByTopNote(@PathVariable String note) {
        return fragranceService.getFragrancesByTopNote(note);
    }
    @GetMapping({"/notes/middle/{note}", "/notes/middle/{note}/"})
    public List<Fragrance> getFragrancesByMiddleNote(@PathVariable String note) {
        return fragranceService.getFragrancesByMiddleNote(note);
    }

    @GetMapping({"/notes/base/{note}", "/notes/base/{note}/"})
    public List<Fragrance> getFragrancesByBaseNote(@PathVariable String note) {
        return fragranceService.getFragrancesByBaseNote(note);
    }

    /*
    @GetMapping("/fragrances/country/{country}")
    public List<Fragrance> getFragrancesByCountry(@PathVariable String country) {
        return fragranceService.getFragrancesByCountry(country);
    }

    @GetMapping("/fragrances/country")
    public List<String> getDistinctCountries() {
        return fragranceService.getDistinctCountries();
    }

    @GetMapping("/fragrances/year/{year}")
    public List<Fragrance> getFragrancesByYear(@PathVariable Integer year) {
        return fragranceService.getFragrancesByYear(year);
    }



    @GetMapping("/fragrances/middle-notes/{middleNotes}")
    public List<Fragrance> getFragrancesByMiddleNotes(@PathVariable String middleNotes) {
        return fragranceService.getFragrancesByMiddleNotes(middleNotes);
    }

    @GetMapping("/fragrances/base-notes/{baseNotes}")
    public List<Fragrance> getFragrancesByBaseNotes(@PathVariable String baseNotes) {
        return fragranceService.getFragrancesByBaseNotes(baseNotes);
    }

    @GetMapping("/fragrances/gender/{gender}")
    public List<Fragrance> getFragrancesByGender(@PathVariable String gender) {
        return fragranceService.getFragrancesByGender(gender);
    }

    @GetMapping("/fragrances/main-accord/{mainAccord}")
    public List<Fragrance> getFragrancesByMainAccord(@PathVariable String mainAccord) {
        return fragranceService.getFragrancesByMainAccord(mainAccord);
    }*/
}
