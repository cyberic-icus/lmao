package ru.rsreu.TrafficCodeServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.TrafficCodeServer.dto.response.AnswerResponseFieldDTO;
import ru.rsreu.TrafficCodeServer.dto.response.QuestionResponseFieldDTO;
import ru.rsreu.TrafficCodeServer.service.entity.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public List<QuestionResponseFieldDTO> getAllQuestions() {
        return questionService.findAll()
                .stream()
                .map(QuestionResponseFieldDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public QuestionResponseFieldDTO getQuestionById(@PathVariable Long id) {
        return questionService.findById(id)
                .map(QuestionResponseFieldDTO::new)
                .orElseGet(QuestionResponseFieldDTO::new);
    }

    @GetMapping("/{id}/answers")
    public List<AnswerResponseFieldDTO> getAnswersByQuestionId(@PathVariable Long id) {
        return questionService.findById(id)
                .map(ticket -> ticket.getAnswers()
                        .stream()
                        .map(AnswerResponseFieldDTO::new)
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }


}
