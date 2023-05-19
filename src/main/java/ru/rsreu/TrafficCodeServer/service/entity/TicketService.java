package ru.rsreu.TrafficCodeServer.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.rsreu.TrafficCodeServer.database.entity.Question;
import ru.rsreu.TrafficCodeServer.database.entity.Ticket;
import ru.rsreu.TrafficCodeServer.database.repository.QuestionRepository;
import ru.rsreu.TrafficCodeServer.dto.request.CheckQuestionDTO;
import ru.rsreu.TrafficCodeServer.dto.request.SubmitTicketDTO;
import ru.rsreu.TrafficCodeServer.dto.response.SubmitTicketQuestionResponseDTO;

import java.util.Collection;
import java.util.List;

@Service
public class TicketService extends AbstractCringeService<Ticket> {

    private final QuestionRepository questionRepository;

    @Autowired
    public TicketService(JpaRepository<Ticket, Long> repository, QuestionRepository questionRepository) {
        super(repository);
        this.questionRepository = questionRepository;
    }

    public List<SubmitTicketQuestionResponseDTO> checkAnswer(SubmitTicketDTO requestTicket) {
        return repository
                .findById(requestTicket.getTicketId())
                .map(ticket ->
                        questionRepository
                                .findAllById(
                                        ticket
                                                .getQuestions()
                                                .stream()
                                                .map(Question::getAnswers)
                                                .flatMap(Collection::stream)
                                                .filter(it -> !it.getCorrect())
                                                .map(CheckQuestionDTO::new)
                                                .distinct()
                                                .filter(requestTicket.getQuestionDTOList()::contains)
                                                .map(CheckQuestionDTO::getQuestionId)
                                                .toList()
                                )
                                .stream()
                                .map(SubmitTicketQuestionResponseDTO::new)
                                .toList()
                ).orElseGet(List::of);
    }


}
