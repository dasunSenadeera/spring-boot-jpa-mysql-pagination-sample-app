package com.example.challenge.models;

import com.example.challenge.enums.TicketPriority;
import com.example.challenge.enums.TicketStatus;
import com.example.challenge.enums.TicketType;
import com.example.challenge.enums.TicketVia;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    private String _id;

    private String url;

    @JsonProperty("external_id")
    private String externalId;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss XXX")
    private LocalDateTime createdAt;

    private TicketType type;

    private String subject;

    private String description;

    private TicketPriority priority;

    private TicketStatus status;

//    private int submitter_id;
    @OneToOne(fetch = FetchType.LAZY)
    private User submitter_id;

//    private int assignee_id;
    @OneToOne(fetch = FetchType.LAZY)
    private User assignee_id;

//    private int organization_id;
    @OneToOne(fetch = FetchType.LAZY)
    private Organization organization_id;

    @ElementCollection
    @CollectionTable(name = "ticket_tags")
    private List<String> tags;

    @JsonProperty("has_incidents")
    private boolean hasIncidents;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("due_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss XXX")
    private LocalDateTime dueAt;

    private TicketVia via;
}
