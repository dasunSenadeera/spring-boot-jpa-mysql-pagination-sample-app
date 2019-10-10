package com.example.challenge.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

//@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TicketStatus {
    PENDING("pending"),
    HOLD("hold"),
    CLOSED("closed"),
    SOLVED("solved"),
    OPEN("open");

    @JsonValue
    private final String value;

    TicketStatus(String value){
        this.value= value;
    }

    public String getValue() {
        return value;
    }
}
