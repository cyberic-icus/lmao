package ru.rsreu.TrafficCodeServer.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.TrafficCodeServer.database.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
