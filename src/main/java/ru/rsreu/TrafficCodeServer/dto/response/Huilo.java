package ru.rsreu.TrafficCodeServer.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.TrafficCodeServer.database.entity.Answer;
import ru.rsreu.TrafficCodeServer.database.entity.Question;

import java.util.Set;

@Data
@NoArgsConstructor
public class Huilo {

    private Question question;
    private Set<Answer> answers;

    public Huilo(Question question, Set<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }
}
