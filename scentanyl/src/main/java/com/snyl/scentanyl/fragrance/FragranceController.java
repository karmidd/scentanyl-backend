package com.snyl.scentanyl.fragrance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class FragranceController {

    @Autowired
    private FragranceService fragranceService;

    @GetMapping({"/fragrances", "/fragrances/"})
    public List<Fragrance> getFragrances() {
        return fragranceService.getFragrances();
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
    public List<Fragrance> getFragrancesByAccord(@PathVariable String accord) {
        return fragranceService.getFragrancesByAccord(accord);
    }

    @GetMapping({"/perfumers/{perfumerName}", "/perfumers/{perfumerName}/"})
    public List<Fragrance> getFragrancesByPerfumer(@PathVariable String perfumerName) {
        return fragranceService.getFragrancesByPerfumer(perfumerName);
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