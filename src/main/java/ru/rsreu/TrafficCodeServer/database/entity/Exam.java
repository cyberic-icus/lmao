package ru.rsreu.TrafficCodeServer.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXAM")
public class Exam {
    @Id
    @GeneratedValue(generator = "exam-sequence-generator")
    @GenericGenerator(
            name = "exam-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "exam_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "EXAM_ID", length = 50, nullable = false)
    private Long id;

    @Column(name = "START_DATE")
    @CreatedDate
    private Instant startDate;

    @Column(name = "NUMBER_CORRECT", length = 2, nullable = false)
    private Long numberCorrect = 0L;

    @Column(name = "END_DATE")
    private Instant endDate;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "TICKET_ID", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;

    @ManyToMany
    @JoinTable(name = "EXAM_INCORRECT_QUESTIONS",
            joinColumns = @JoinColumn(name = "EXAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "QUESTION_ID")
    )
    private Set<Question> incorrectQuestions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Exam exam = (Exam) o;
        return id != null && Objects.equals(id, exam.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}