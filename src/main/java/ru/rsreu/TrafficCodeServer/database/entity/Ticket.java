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
@Table(name = "TICKET")
public class Ticket {
    @Id
    @GeneratedValue(generator = "ticket-sequence-generator")
    @GenericGenerator(
            name = "ticket-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ticket_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "TICKET_ID", length = 50, nullable = false)
    private Long id;

    @Column(name = "TICKET_TYPE", length = 50, nullable = false)
    private String type;

    @Column(name = "TICKET_YEAR", nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Set<Question> questions;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Set<Exam> exams;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ticket ticket = (Ticket) o;
        return id != null && Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}