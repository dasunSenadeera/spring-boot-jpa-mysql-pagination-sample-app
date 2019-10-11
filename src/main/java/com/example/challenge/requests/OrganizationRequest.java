package com.example.challenge.requests;

import java.time.LocalDateTime;
import java.util.List;

public class OrganizationRequest {

    private long _id;

    private String url;

    private String externalId;

    private String name;

    private List<String> domain;

    private LocalDateTime createdAt;

    private String details;

    private boolean sharedTickets;

    private List<String> tags;
}
