package ru.rsreu.TrafficCodeServer.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.TrafficCodeServer.database.entity.Answer;

@Data
@NoArgsConstructor
public class CheckQuestionDTO {
    private Long questionId;
    private Long answerId;

    public CheckQuestionDTO(Answer answer) {
        this.questionId = answer.getQuestion().getId();
        this.answerId = answer.getId();
    }
}
