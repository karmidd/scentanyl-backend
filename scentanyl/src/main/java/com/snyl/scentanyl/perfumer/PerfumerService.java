package com.snyl.scentanyl.perfumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class PerfumerService {
    private final PerfumerRepository perfumerRepository;

    public List<Perfumer> getPerfumers() {
        return perfumerRepository.findAll();
    }

    public Optional<Perfumer> getPerfumerByName(String name) {
        return perfumerRepository.findByNameIgnoreCase(name);
    }
}
