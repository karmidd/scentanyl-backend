package com.snyl.scentanyl.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandByName(String name) {
        return brandRepository.findByNameIgnoreCase(name);
    }
}
