package ru.rsreu.TrafficCodeServer.database.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(generator = "role-sequence-generator")
    @GenericGenerator(
            name = "role-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "role_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public RoleEntity(ERole name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}