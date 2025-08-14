package com.snyl.scentanyl.perfumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PerfumerService {
    private final PerfumerRepository perfumerRepository;

    public List<Perfumer> getPerfumers() {
        return perfumerRepository.findAll();
    }
}
