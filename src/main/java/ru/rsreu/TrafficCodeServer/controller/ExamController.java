package ru.rsreu.TrafficCodeServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.TrafficCodeServer.database.entity.Exam;
import ru.rsreu.TrafficCodeServer.database.entity.Question;
import ru.rsreu.TrafficCodeServer.database.entity.UserEntity;
import ru.rsreu.TrafficCodeServer.database.repository.UserRepository;
import ru.rsreu.TrafficCodeServer.dto.response.ExamDTO;
import ru.rsreu.TrafficCodeServer.dto.response.ExamIdDTO;
import ru.rsreu.TrafficCodeServer.service.entity.ExamService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class ExamController {
    private final ExamService examService;
    private final UserRepository userRepository;

    @Autowired
    public ExamController(ExamService examService,
                          UserRepository userRepository) {
        this.examService = examService;
        this.userRepository = userRepository;
    }


    @GetMapping("/{id}")
    public ExamDTO getExamById(@PathVariable Long id) {
        var exam = examService.findById(id).get();
        return new ExamDTO(
                        exam.getId(),
                        exam.getStartDate(),
                        exam.getNumberCorrect(),
                        exam.getEndDate(),
                        exam.getIsActive(),
                        exam.getTicket().getId(),
                        exam.getIncorrectQuestions()
                                .stream()
                                .map(Question::getId)
                                .collect(Collectors.toSet())
                );
    }

    @GetMapping("/user/")
    public List<ExamDTO> getUsersExams() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
        return user.map(userEntity -> userEntity
                .getExams()
                .stream()
                .map(exam -> new ExamDTO(
                        exam.getId(),
                        exam.getStartDate(),
                        exam.getNumberCorrect(),
                        exam.getEndDate(),
                        exam.getIsActive(),
                        exam.getTicket().getId(),
                        exam.getIncorrectQuestions()
                                .stream()
                                .map(Question::getId)
                                .collect(Collectors.toSet())
                ))
                .toList()).orElse(null);
    }

    @GetMapping("/start/{ticket}")
    public ExamIdDTO startExam(@PathVariable Long ticket) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ExamIdDTO(examService.startExam(authentication, ticket));
    }

    @PostMapping("/end")
    public ExamDTO endExam(@RequestBody ru.rsreu.TrafficCodeServer.dto.request.SubmitTicketDTO submitTicketDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isPresent()) {
            Exam exam = examService.endExam(user.get(), submitTicketDTO);
            return new ExamDTO(
                    exam.getId(),
                    exam.getStartDate(),
                    exam.getNumberCorrect(),
                    exam.getEndDate(),
                    exam.getIsActive(),
                    exam.getTicket().getId(),
                    exam.getIncorrectQuestions()
                            .stream()
                            .map(Question::getId)
                            .collect(Collectors.toSet())
            );
        }

        return null;
    }
}
