package com.example.challenge.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Organization")
public class Organization {

    @Id
    private long _id;

    private String url;

    @JsonProperty("external_id")
    private String externalId;

    private String name;

    @ElementCollection
    @JsonProperty("domain_names")
    @CollectionTable(name = "organization_domain_names")
    private List<String> domain;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss XXX")
    private LocalDateTime createdAt;

    private String details;

    @JsonProperty("shared_tickets")
    private boolean sharedTickets;

    @ElementCollection
    @JsonProperty("tags")
    @CollectionTable(name = "organization_tags")
    private List<String> tags;
}
