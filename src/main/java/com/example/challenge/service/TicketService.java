package com.example.challenge.service;

import com.example.challenge.enums.TicketPriority;
import com.example.challenge.enums.TicketStatus;
import com.example.challenge.enums.TicketType;
import com.example.challenge.enums.TicketVia;
import com.example.challenge.models.Ticket;
import com.example.challenge.repository.OrganizationRepository;
import com.example.challenge.repository.TicketRepository;
import com.example.challenge.requests.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public void saveInitialTickets(List<TicketRequest> ticketRequestList){
        ticketRequestList.forEach(ticketRequest -> {
            ticketRepository.save(new Ticket(ticketRequest.get_id(), ticketRequest.getUrl(), ticketRequest.getExternalId(), ticketRequest.getCreated_at(), ticketRequest.getType(), ticketRequest.getSubject(), ticketRequest.getDescription(), ticketRequest.getPriority(), ticketRequest.getStatus(), ticketRequest.getSubmitter_id(), ticketRequest.getAssignee_id(), organizationRepository.findById(Long.valueOf(ticketRequest.getOrganization_id())).orElse(null), ticketRequest.getTags(), ticketRequest.isHasIncidents(), ticketRequest.getDue_at(), ticketRequest.getVia()));
        });
    }

    public Optional<Ticket> getTicket(String id){
        return ticketRepository.findById(id);
    }

    public Page<Ticket> findAll(int page, int size){
        return ticketRepository.findAll(PageRequest.of(page, size));
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

    public Page<Ticket> findByTicketType(TicketType ticketType,int page, int size){
        return ticketRepository.findByType(ticketType,PageRequest.of(page, size));
    }

    public Page<Ticket> findBySubject(String subject,int page, int size){
        return ticketRepository.findBySubject(subject,PageRequest.of(page, size));
    }

    public Page<Ticket> findByTicketPriority(TicketPriority ticketPriority, int page, int size){
        return ticketRepository.findByPriority(ticketPriority,PageRequest.of(page, size));
    }

    public Page<Ticket> findByTicketStatus(TicketStatus ticketStatus, int page, int size){
        return ticketRepository.findByStatus(ticketStatus,PageRequest.of(page, size));
    }

    public Page<Ticket> findTicketsByTagNames(List<String> tagslist, int page, int size){
        return ticketRepository.findByTagsIn(tagslist, PageRequest.of(page, size));
    }

    public Page<Ticket> findByHasIncidents(boolean hasIncidents,int page, int size ){
        return ticketRepository.findByHasIncidents(hasIncidents, PageRequest.of(page, size));
    }

    public Page<Ticket> findByTicketVia(TicketVia ticketVia, int page, int size){
        return ticketRepository.findByVia(ticketVia,PageRequest.of(page, size));
    }

    public Page<Ticket> findAllByDueAt(LocalDateTime dateTime, int page, int size){
        return ticketRepository.findAllByDueAt(dateTime, PageRequest.of(page, size));
    }

    public List<Ticket> findAllByBeforeDueAt(LocalDateTime dateTime){
        return ticketRepository.findAllWithDueDateTimeBefore(dateTime);
    }

    public List<Ticket> findAllByAfterDueAt(LocalDateTime dateTime){
        return ticketRepository.findAllWithDueDateTimeAfter(dateTime);
    }

    public Page<Ticket> findAllOrganizationsDueBetween(LocalDateTime start, LocalDateTime end, int page, int size){
        return ticketRepository.findAllByDueAtBetween(start, end, PageRequest.of(page, size));
    }

    public List<Ticket> findByTicketViaAndStatus(TicketVia ticketVia, TicketStatus ticketStatus){
        return ticketRepository.findByViaAndStatus(ticketVia, ticketStatus);
    }

    public List<Ticket> findByTicketViaAndStatusAndPriority(TicketVia ticketVia, TicketStatus ticketStatus, TicketPriority ticketPriority){
        return ticketRepository.findByViaAndStatusAndPriority(ticketVia, ticketStatus, ticketPriority);
    }




}
