package ru.rsreu.TrafficCodeServer.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rsreu.TrafficCodeServer.database.entity.Question;
import ru.rsreu.TrafficCodeServer.database.repository.QuestionRepository;

@Service
public class QuestionService extends AbstractCringeService<Question> {
    @Autowired
    public QuestionService(QuestionRepository repository) {
        super(repository);
    }
}
