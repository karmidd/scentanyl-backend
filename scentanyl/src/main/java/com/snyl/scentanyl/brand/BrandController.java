package com.snyl.scentanyl.brand;

import com.snyl.scentanyl.fragrance.Fragrance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping({"/brands", "/brands/"})
    public List<Brand> getBrands() {
        return brandService.getBrands();
    }

    @GetMapping({"/brands/{brand}/info", "/brands/{brand}/info"})
    public Optional<Brand> getBrand(@PathVariable String brand) {
        return brandService.getBrandByName(brand);
    }

    @GetMapping({"/random-brand", "/random-brand/"})
    public Optional<Brand> getRandomBrand() {
        return brandService.getRandomBrand();
    }
}
