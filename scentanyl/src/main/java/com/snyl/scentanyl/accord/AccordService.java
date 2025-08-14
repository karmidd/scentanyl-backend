package com.snyl.scentanyl.accord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccordService {
    private final AccordRepository accordRepository;

    public List<Accord> getAccords() {
        return accordRepository.findAll();
    }
}
