package ru.rsreu.TrafficCodeServer.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(generator = "question-sequence-generator")
    @GenericGenerator(
            name = "question-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "question_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "QUESTION_ID", length = 50, nullable = false)
    private Long id;

    @Column(name = "IMAGE", length = 2048)
    private String image;

    @Column(name = "EXPLANATION", length = 2048, nullable = false)
    private String explanation;

    @Column(name = "QUESTION_TEXT", length = 2048, nullable = false)
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "TICKET_ID", nullable = false)
    private Ticket ticket;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Question question = (Question) o;
        return id != null && Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}