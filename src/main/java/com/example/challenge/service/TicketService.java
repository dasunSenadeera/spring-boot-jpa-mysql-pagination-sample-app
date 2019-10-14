package com.example.challenge.service;

import com.example.challenge.enums.TicketPriority;
import com.example.challenge.enums.TicketStatus;
import com.example.challenge.enums.TicketType;
import com.example.challenge.enums.TicketVia;
import com.example.challenge.models.Ticket;
import com.example.challenge.repository.OrganizationRepository;
import com.example.challenge.repository.TicketRepository;
import com.example.challenge.repository.UserRepository;
import com.example.challenge.requests.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserRepository userRepository;

    public void saveInitialTickets(List<TicketRequest> ticketRequestList){
        ticketRequestList.forEach(ticketRequest -> {
            ticketRepository.save(new Ticket(ticketRequest.get_id(), ticketRequest.getUrl(), ticketRequest.getExternalId(), ticketRequest.getCreated_at(), ticketRequest.getType(), ticketRequest.getSubject(), ticketRequest.getDescription(), ticketRequest.getPriority(), ticketRequest.getStatus(),userRepository.findById(Long.valueOf(ticketRequest.getSubmitter_id())).orElse(null) , userRepository.findById(Long.valueOf(ticketRequest.getAssignee_id())).orElse(null) , organizationRepository.findById(Long.valueOf(ticketRequest.getOrganization_id())).orElse(null), ticketRequest.getTags(), ticketRequest.isHasIncidents(), ticketRequest.getDue_at(), ticketRequest.getVia()));
        });
    }

    public Optional<Ticket> getTicket(String id){
        return ticketRepository.findById(id);
    }

    public Page<Ticket> findAll(Pageable pageable){
        return ticketRepository.findAll(pageable);
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findByUrl(String url){
        return ticketRepository.findByUrl(url);
    }

    public Optional<Ticket> findByExternalID(String externalId){
        return ticketRepository.findByExternalId(externalId);
    }

    public List<Ticket> findAllByCreatedAt(LocalDateTime dateTime){
        return ticketRepository.findAllByCreatedAt(dateTime);
    }

    public List<Ticket> findAllByBeforeCreatedAt(LocalDateTime dateTime){
        return ticketRepository.findAllWithCreationDateTimeBefore(dateTime);
    }

    public List<Ticket> findAllByAfterCreatedAt(LocalDateTime dateTime){
        return ticketRepository.findAllWithCreationDateTimeAfter(dateTime);
    }

    public List<Ticket> findAllTicketsCreatedBetween(LocalDateTime start, LocalDateTime end){
        return ticketRepository.findAllByCreatedAtBetween(start, end);
    }

    public Page<Ticket> findByTicketType(TicketType ticketType,Pageable pageable){
        return ticketRepository.findByType(ticketType,pageable);
    }

    public Page<Ticket> findBySubject(String subject,Pageable pageable){
        return ticketRepository.findBySubject(subject,pageable);
    }

    public Page<Ticket> findByTicketPriority(TicketPriority ticketPriority, Pageable pageable){
        return ticketRepository.findByPriority(ticketPriority,pageable);
    }

    public Page<Ticket> findByTicketStatus(TicketStatus ticketStatus, Pageable pageable){
        return ticketRepository.findByStatus(ticketStatus,pageable);
    }

    public Page<Ticket> findTicketsByTagNames(List<String> tagslist, Pageable pageable){
        return ticketRepository.findByTagsIn(tagslist, pageable);
    }

    public Page<Ticket> findByHasIncidents(boolean hasIncidents,Pageable pageable ){
        return ticketRepository.findByHasIncidents(hasIncidents, pageable);
    }

    public Page<Ticket> findByTicketVia(TicketVia ticketVia, Pageable pageable){
        return ticketRepository.findByVia(ticketVia,pageable);
    }

    public Page<Ticket> findAllByDueAt(LocalDateTime dateTime, Pageable pageable){
        return ticketRepository.findAllByDueAt(dateTime, pageable);
    }

    public List<Ticket> findAllByBeforeDueAt(LocalDateTime dateTime){
        return ticketRepository.findAllWithDueDateTimeBefore(dateTime);
    }

    public List<Ticket> findAllByAfterDueAt(LocalDateTime dateTime){
        return ticketRepository.findAllWithDueDateTimeAfter(dateTime);
    }

    public Page<Ticket> findAllOrganizationsDueBetween(LocalDateTime start, LocalDateTime end, Pageable pageable){
        return ticketRepository.findAllByDueAtBetween(start, end, pageable);
    }

    public List<Ticket> findByTicketViaAndStatus(TicketVia ticketVia, TicketStatus ticketStatus){
        return ticketRepository.findByViaAndStatus(ticketVia, ticketStatus);
    }

    public List<Ticket> findByTicketViaAndStatusAndPriority(TicketVia ticketVia, TicketStatus ticketStatus, TicketPriority ticketPriority){
        return ticketRepository.findByViaAndStatusAndPriority(ticketVia, ticketStatus, ticketPriority);
    }




}
