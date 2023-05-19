package ru.rsreu.TrafficCodeServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.TrafficCodeServer.dto.request.SubmitTicketDTO;
import ru.rsreu.TrafficCodeServer.dto.response.QuestionResponseFieldDTO;
import ru.rsreu.TrafficCodeServer.dto.response.SubmitTicketQuestionResponseDTO;
import ru.rsreu.TrafficCodeServer.dto.response.TicketResponseFieldDTO;
import ru.rsreu.TrafficCodeServer.service.entity.TicketService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public List<TicketResponseFieldDTO> getAllTickets() {
        return ticketService.findAll()
                .stream()
                .map(TicketResponseFieldDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TicketResponseFieldDTO getTicketById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(TicketResponseFieldDTO::new)
                .orElseGet(TicketResponseFieldDTO::new);
    }

    @GetMapping("/{id}/questions")
    public List<QuestionResponseFieldDTO> getQuestionsByTicketId(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ticket -> ticket.getQuestions()
                        .stream()
                        .map(QuestionResponseFieldDTO::new)
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }

    @PostMapping("/submit")
    public List<SubmitTicketQuestionResponseDTO> submitTicket(@RequestBody SubmitTicketDTO ticket) {
        return ticketService.checkAnswer(ticket);
    }

}
