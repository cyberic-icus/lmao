package ru.rsreu.TrafficCodeServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.TrafficCodeServer.dto.response.AnswerResponseFieldDTO;
import ru.rsreu.TrafficCodeServer.service.entity.AnswerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answers")
@CrossOrigin(origins = "*")
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/")
    public List<AnswerResponseFieldDTO> getAllAnswers() {
        return answerService.findAll()
                .stream()
                .map(AnswerResponseFieldDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AnswerResponseFieldDTO getAnswerById(@PathVariable Long id) {
        return answerService.findById(id)
                .map(AnswerResponseFieldDTO::new)
                .orElseGet(AnswerResponseFieldDTO::new);
    }
}
