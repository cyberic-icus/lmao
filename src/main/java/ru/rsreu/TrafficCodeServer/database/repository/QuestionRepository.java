package ru.rsreu.TrafficCodeServer.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.TrafficCodeServer.database.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
