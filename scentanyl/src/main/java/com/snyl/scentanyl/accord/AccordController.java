package com.snyl.scentanyl.accord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccordController {
    @Autowired
    private AccordService accordService;

    @GetMapping({"/accords", "/accords/"})
    public List<Accord> getAccords() {
        return accordService.getAccords();
    }
}
