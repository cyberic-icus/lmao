package ru.rsreu.TrafficCodeServer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SubmitTicketDTO {
    private Long ticketId;

    @JsonProperty("question")
    private List<CheckQuestionDTO> questionDTOList;
}
