package ru.rsreu.TrafficCodeServer.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.TrafficCodeServer.database.entity.Question;

@Data
@NoArgsConstructor
public class SubmitTicketQuestionResponseDTO {
    private Long questionId;
    private String explanation;

    public SubmitTicketQuestionResponseDTO(Question question) {
        this.questionId = question.getId();
        this.explanation = question.getExplanation();
    }
}
