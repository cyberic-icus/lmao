package ru.rsreu.TrafficCodeServer.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.TrafficCodeServer.database.entity.Answer;

@Data
@NoArgsConstructor
public class AnswerResponseFieldDTO {
    private Long id;
    private Boolean correct;
    private String text;

    public AnswerResponseFieldDTO(Answer answer) {
        this.id = answer.getId();
        this.correct = answer.getCorrect();
        this.text = answer.getText();
    }
}
