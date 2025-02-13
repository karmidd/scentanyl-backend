package com.snyl.scentanyl.fragrance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class FragranceController {

    @Autowired
    private FragranceService fragranceService;

    @GetMapping("/fragrances")
    public List<Fragrance> getFragrances() {
        return fragranceService.getFragrances();
    }
    @GetMapping("/fragrances/brands")
    public List<String> getDistinctBrands() {
        return fragranceService.getDistinctBrands();
    }
    @GetMapping("/fragrances/brands/{brand}")
    public List<Fragrance> getFragrancesByBrand(@PathVariable String brand) {
        return fragranceService.getFragrancesByBrand(brand);
    }
    @GetMapping("/fragrances/{brand}/{name}")
    public Optional<Fragrance> getFragrance(@PathVariable String brand, @PathVariable String name) {
        return fragranceService.getFragranceByName(name);
    }
    @GetMapping("/fragrances/country/{country}")
    public List<Fragrance> getFragrancesByCountry(@PathVariable String country) {
        return fragranceService.getFragrancesByCountry(country);
    }
    @GetMapping("/fragrances/year/{year}")
    public List<Fragrance> getFragrancesByYear(@PathVariable Integer year) {
        return fragranceService.getFragrancesByYear(year);
    }
    @GetMapping("/fragrances/perfumer/{perfumer}")
    public List<Fragrance> getFragrancesByPerfumer(@PathVariable String perfumer) {
        return fragranceService.getFragrancesByPerfumer(perfumer);
    }
    @GetMapping("/fragrances/top-notes/{topNotes}")
    public List<Fragrance> getFragrancesByTopNotes(@PathVariable String topNotes) {
        return fragranceService.getFragrancesByTopNotes(topNotes);
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
    }

}
