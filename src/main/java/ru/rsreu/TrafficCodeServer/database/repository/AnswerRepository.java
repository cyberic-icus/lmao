package ru.rsreu.TrafficCodeServer.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.TrafficCodeServer.database.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
