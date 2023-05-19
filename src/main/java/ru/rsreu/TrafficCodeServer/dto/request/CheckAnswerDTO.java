package ru.rsreu.TrafficCodeServer.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckAnswerDTO {
    private Long questionId;
    private Long answerId;
}
