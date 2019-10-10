package com.example.challenge.repository;

import com.example.challenge.enums.TicketPriority;
import com.example.challenge.enums.TicketStatus;
import com.example.challenge.enums.TicketType;
import com.example.challenge.enums.TicketVia;
import com.example.challenge.models.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    Optional<Ticket> findByUrl(String url);

    Optional<Ticket> findByExternalId(String externalId);

    List<Ticket> findAllByCreatedAt(LocalDateTime createdDate);

    List<Ticket> findAllByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

    @Query("select t from Ticket t where t.createdAt <= :createdDate")
    List<Ticket> findAllWithCreationDateTimeBefore(LocalDateTime createdDate);

    @Query("select t from Ticket t where t.createdAt >= :createdDate")
    List<Ticket> findAllWithCreationDateTimeAfter(LocalDateTime createdDate);

    Page<Ticket> findByType(TicketType type, Pageable pageable);

    Page<Ticket> findByVia(TicketVia via, Pageable pageable);

    Page<Ticket> findBySubject(String subject, Pageable pageable);

    Page<Ticket> findByPriority(TicketPriority ticketPriority, Pageable pageable);

    Page<Ticket> findByStatus(TicketStatus ticketStatus, Pageable pageable);

    Page<Ticket> findByTagsIn(Collection<String> tags, Pageable pageable);

    Page<Ticket> findByHasIncidents(boolean hasIncidents, Pageable pageable);

    Page<Ticket> findAllByDueAt(LocalDateTime createdDate, Pageable pageable);

    Page<Ticket> findAllByDueAtBetween(
            LocalDateTime start,
            LocalDateTime end, Pageable pageable);

    @Query("select t from Ticket t where t.createdAt <= :createdDate")
    List<Ticket> findAllWithDueDateTimeBefore(LocalDateTime createdDate);

    @Query("select t from Ticket t where t.createdAt >= :createdDate")
    List<Ticket> findAllWithDueDateTimeAfter(LocalDateTime createdDate);

}
