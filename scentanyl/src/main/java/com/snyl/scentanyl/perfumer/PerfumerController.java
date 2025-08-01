package com.snyl.scentanyl.perfumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PerfumerController {
    @Autowired
    private PerfumerService perfumerService;

    @GetMapping({"/perfumers", "/perfumers/"})
    public List<Perfumer> getPerfumers() {
        return perfumerService.getPerfumers();
    }
}
