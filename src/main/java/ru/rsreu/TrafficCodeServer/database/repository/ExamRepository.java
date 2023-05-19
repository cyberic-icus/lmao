package ru.rsreu.TrafficCodeServer.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.TrafficCodeServer.database.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
