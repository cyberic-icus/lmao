package ru.rsreu.TrafficCodeServer.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.rsreu.TrafficCodeServer.database.entity.Answer;
import ru.rsreu.TrafficCodeServer.database.entity.Exam;
import ru.rsreu.TrafficCodeServer.database.entity.Ticket;
import ru.rsreu.TrafficCodeServer.database.entity.UserEntity;
import ru.rsreu.TrafficCodeServer.database.repository.TicketRepository;
import ru.rsreu.TrafficCodeServer.database.repository.UserRepository;
import ru.rsreu.TrafficCodeServer.dto.request.CheckQuestionDTO;
import ru.rsreu.TrafficCodeServer.dto.request.SubmitTicketDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ExamService extends AbstractCringeService<Exam> {

    private static final int NUMBER_OF_ANSWERS = 20;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public ExamService(JpaRepository<Exam, Long> repository, UserRepository userRepository, TicketRepository ticketRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    public Long startExam(Authentication authentication, Long ticket) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticket);
        if (user.isPresent() &&
                ticketOptional.isPresent()
                &&
                user.get()
                        .getExams()
                        .stream()
                        .filter(Exam::getIsActive)
                        .collect(Collectors.toSet()).isEmpty()
        ) {
            Exam exam = new Exam();
            exam.setUser(user.get());
            exam.setTicket(ticketOptional.get());
            return repository.save(exam).getId();
        }
        return null;
    }

    public Exam endExam(UserEntity user, SubmitTicketDTO submitTicketDTO) {
        if (!user.getExams()
                .stream()
                .filter(it -> !it.getIsActive())
                .collect(Collectors.toSet()).isEmpty()
        ) {
            Optional<Exam> examOptional = user
                    .getExams()
                    .stream()
                    .filter(it -> !it.getIsActive())
                    .findFirst();
            if (examOptional.isPresent()) {
                var exam = examOptional.get();
                calculateResults(exam, submitTicketDTO);
                exam.setIsActive(false);
                exam.setEndDate(new Date().toInstant());
                return repository.save(exam);
            }
        }
        return null;
    }

    private void calculateResults(Exam exam, SubmitTicketDTO submitTicketDTO) {
        Ticket ticket = exam.getTicket();

        List<Long> answersIds = submitTicketDTO.getQuestionDTOList()
                .stream().map(CheckQuestionDTO::getAnswerId)
                .toList();

        var incorrectQuestions = ticket.getQuestions()
                .stream()
                .flatMap(it -> it.getAnswers().stream())
                .filter(it -> it.getCorrect() && !answersIds.contains(it.getId()))
                .map(Answer::getQuestion)
                .collect(Collectors.toSet());

        exam.setNumberCorrect((long) (NUMBER_OF_ANSWERS - incorrectQuestions.size()));
        exam.setIncorrectQuestions(incorrectQuestions);

    }

}
