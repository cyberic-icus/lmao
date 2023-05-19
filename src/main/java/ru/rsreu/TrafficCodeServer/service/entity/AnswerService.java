package ru.rsreu.TrafficCodeServer.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rsreu.TrafficCodeServer.database.entity.Answer;
import ru.rsreu.TrafficCodeServer.database.repository.AnswerRepository;

@Service
public class AnswerService extends AbstractCringeService<Answer> {

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        super(answerRepository);
    }
}
