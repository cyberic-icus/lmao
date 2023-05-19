package ru.rsreu.TrafficCodeServer.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.TrafficCodeServer.database.entity.Ticket;

@Data
@NoArgsConstructor
public class TicketResponseFieldDTO {
    private Long id;

    private String type;

    private Integer year;

    public TicketResponseFieldDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.type = ticket.getType();
        this.year = ticket.getYear();
    }
}
