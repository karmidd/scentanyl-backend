package com.snyl.scentanyl.fragrance;

import com.snyl.scentanyl.accord.Accord;
import com.snyl.scentanyl.brand.Brand;
import com.snyl.scentanyl.note.Note;
import com.snyl.scentanyl.perfumer.Perfumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class FragranceController {

    @Autowired
    private FragranceService fragranceService;

    @GetMapping({"/fragrances", "/fragrances/"})
    public List<Fragrance> getFragrances() {
        return fragranceService.getFragrances();
    }

    @GetMapping({"/brands", "/brands/"})
    public List<Brand> getBrands() {
        return fragranceService.getBrands();
    }

    @GetMapping({"/brands/{brand}/info", "/brands/{brand}/info"})
    public Optional<Brand> getBrand(@PathVariable String brand) {
        return fragranceService.getBrandByName(brand);
    }

    @GetMapping({"/brands/{brand}", "/brands/{brand}/"})
    public List<Fragrance> getFragrancesByBrand(@PathVariable String brand) {
        return fragranceService.getFragrancesByBrand(brand);
    }

    @GetMapping({"/fragrances/{brand}/{name}", "/fragrances/{brand}/{name}/"})
    public Optional<Fragrance> getFragrance(@PathVariable String brand, @PathVariable String name) {
        return fragranceService.getFragranceByBrandAndName(brand, name);
    }

    @GetMapping({"/perfumers", "/perfumers/"})
    public List<Perfumer> getPerfumers() {
        return fragranceService.getPerfumers();
    }

    @GetMapping({"/accords", "/accords/"})
    public List<Accord> getAccords() {
        return fragranceService.getAccords();
    }

    @GetMapping({"/accords/{accord}", "/accords/{accord}/"})
    public List<Fragrance> getFragrancesByAccord(@PathVariable String accord) {
        return fragranceService.getFragrancesByAccord(accord);
    }

    @GetMapping({"/notes", "/notes/"})
    public List<Note> getNotes() {
        return fragranceService.getNotes();
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

    @GetMapping({"/random", "/random/"})
    public Optional<Fragrance> getRandomFragrance() {
        return fragranceService.getRandomFragrance();
    }
}