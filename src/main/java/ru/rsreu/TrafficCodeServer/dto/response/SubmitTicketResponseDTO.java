package ru.rsreu.TrafficCodeServer.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SubmitTicketResponseDTO {
    private List<SubmitTicketQuestionResponseDTO> wrongAnswers;

    public SubmitTicketResponseDTO(List<SubmitTicketQuestionResponseDTO> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }


}
