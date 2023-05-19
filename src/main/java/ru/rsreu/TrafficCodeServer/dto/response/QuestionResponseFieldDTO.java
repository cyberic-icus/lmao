package ru.rsreu.TrafficCodeServer.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.TrafficCodeServer.database.entity.Question;

@Data
@NoArgsConstructor
public class QuestionResponseFieldDTO {
    private Long id;

    private String image;

    private String questionText;

    private String explanation;

    public QuestionResponseFieldDTO(Question question) {
        this.id = question.getId();
        this.image = question.getImage();
        this.explanation = question.getExplanation();
        this.questionText = question.getQuestionText();
    }
}
