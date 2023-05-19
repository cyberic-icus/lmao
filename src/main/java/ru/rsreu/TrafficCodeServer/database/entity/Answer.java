package ru.rsreu.TrafficCodeServer.database.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ANSWER")
public class Answer {
    @Id
    @GeneratedValue(generator = "answer-sequence-generator")
    @GenericGenerator(
            name = "answer-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "answer_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "ANSWER_ID", length = 50, nullable = false)
    private Long id;

    @Column(name = "CORRECT", nullable = false)
    private Boolean correct;
    @Column(name = "ANSWER_TEXT", length = 2048, nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Question question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Answer answer = (Answer) o;
        return id != null && Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}