package com.snyl.scentanyl.fragrance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class FragranceController {

    @Autowired
    private FragranceService fragranceService;

    @GetMapping({"/all-fragrances", "/all-fragrances/"})
    public List<Fragrance> getFragrances() {
        return fragranceService.getFragrances();
    }

    @GetMapping({"/fragrances", "/fragrances/"})
    public Page<Fragrance> getFragrancesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) String advancedMode,
            @RequestParam(required = false) String accords,
            @RequestParam(required = false) String excludedAccords,
            @RequestParam(required = false) String topNotes,
            @RequestParam(required = false) String middleNotes,
            @RequestParam(required = false) String baseNotes,
            @RequestParam(required = false) String excludedTopNotes,
            @RequestParam(required = false) String excludedMiddleNotes,
            @RequestParam(required = false) String excludedBaseNotes,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String excludedNotes
    ) {
        return fragranceService.getFragrancesFiltered(
                page, size, search, gender, minYear, maxYear, sortBy, sortDirection,
                advancedMode, accords, excludedAccords, topNotes, middleNotes, baseNotes,
                excludedTopNotes, excludedMiddleNotes, excludedBaseNotes, notes, excludedNotes
        );
    }

    @GetMapping({"/fragrances/stats", "/fragrances/stats/"})
    public Map<String, Object> getFragranceStats() {
        return fragranceService.getFragranceStats();
    }

    @GetMapping({"/accords/{accord}/stats", "/accords/{accord}/stats/"})
    public Map<String, Object> getAccordStats(@PathVariable String accord) {
        return fragranceService.getAccordStats(accord);
    }

    @GetMapping({"/brands/{brand}", "/brands/{brand}/"})
    public List<Fragrance> getFragrancesByBrand(@PathVariable String brand) {
        return fragranceService.getFragrancesByBrand(brand);
    }

    @GetMapping({"/fragrances/{brand}/{name}", "/fragrances/{brand}/{name}/"})
    public Optional<Fragrance> getFragrance(@PathVariable String brand, @PathVariable String name) {
        return fragranceService.getFragranceByBrandAndName(brand, name);
    }

    @GetMapping({"/fragrances/{brand}/{name}/{id}", "/fragrances/{brand}/{name}/{id}"})
    public Optional<Fragrance> getFragranceWithId(@PathVariable String brand, @PathVariable String name, @PathVariable Long id) {
        return fragranceService.getFragranceByBrandAndNameAndId(brand, name, id);
    }

    @GetMapping({"/accords/{accord}", "/accords/{accord}/"})
    public Page<Fragrance> getFragrancesByAccord(
            @PathVariable String accord,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) String advancedMode,
            @RequestParam(required = false) String accords,
            @RequestParam(required = false) String excludedAccords,
            @RequestParam(required = false) String topNotes,
            @RequestParam(required = false) String middleNotes,
            @RequestParam(required = false) String baseNotes,
            @RequestParam(required = false) String excludedTopNotes,
            @RequestParam(required = false) String excludedMiddleNotes,
            @RequestParam(required = false) String excludedBaseNotes,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String excludedNotes
    ) {
        return fragranceService.getFragrancesByAccordPaginated(
                accord, page, size, search, gender, minYear, maxYear, sortBy, sortDirection,
                advancedMode, accords, excludedAccords, topNotes, middleNotes, baseNotes,
                excludedTopNotes, excludedMiddleNotes, excludedBaseNotes, notes, excludedNotes
        );
    }

    @GetMapping({"/perfumers/{perfumerName}", "/perfumers/{perfumerName}/"})
    public List<Fragrance> getFragrancesByPerfumer(@PathVariable String perfumerName) {
        return fragranceService.getFragrancesByPerfumer(perfumerName);
    }

    @GetMapping({"/notes/{note}", "/notes/{note}/"})
    public Page<Fragrance> getFragrancesByNote(
            @PathVariable String note,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String advancedMode,
            @RequestParam(required = false) String accords,
            @RequestParam(required = false) String excludedAccords,
            @RequestParam(required = false) String topNotes,
            @RequestParam(required = false) String middleNotes,
            @RequestParam(required = false) String baseNotes,
            @RequestParam(required = false) String excludedTopNotes,
            @RequestParam(required = false) String excludedMiddleNotes,
            @RequestParam(required = false) String excludedBaseNotes,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String excludedNotes
    ) {
        return fragranceService.getFragrancesByNotePaginated(
                note, position, page, size, search, gender, minYear, maxYear, sortBy, sortDirection,
                advancedMode, accords, excludedAccords, topNotes, middleNotes, baseNotes,
                excludedTopNotes, excludedMiddleNotes, excludedBaseNotes, notes, excludedNotes
        );
    }

    @GetMapping({"/notes/{note}/stats", "/notes/{note}/stats/"})
    public Map<String, Object> getNoteStats(@PathVariable String note) {
        return fragranceService.getNoteStats(note);
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

    @GetMapping({"/random-frag", "/random-frag/"})
    public ResponseEntity<?> getRandomFragrance(@RequestParam(defaultValue = "1") int count) {
        if (count <= 1) {
            Optional<Fragrance> frag = fragranceService.getRandomFragrance();
            return frag.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            List<Fragrance> list = fragranceService.getRandomFragrances(count);
            return ResponseEntity.ok(list);
        }
    }
}