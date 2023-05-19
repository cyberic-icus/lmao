package ru.rsreu.TrafficCodeServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
public class ExamDTO {
    private Long id;

    private Instant startDate;

    private Long numberCorrect;

    private Instant endDate;

    private Boolean isActive;

    private Long ticket;

    private Set<Long> incorrectQuestions;
}