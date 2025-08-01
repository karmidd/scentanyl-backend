package com.snyl.scentanyl.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
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
    public ResponseEntity<?> getRandomBrand(@RequestParam(defaultValue = "1") int count) {
        if (count <= 1) {
            Optional<Brand> brand = brandService.getRandomBrand();
            return brand.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            List<Brand> list = brandService.getRandomBrands(count);
            return ResponseEntity.ok(list);
        }
    }
}
