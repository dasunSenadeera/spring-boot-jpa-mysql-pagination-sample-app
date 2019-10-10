package com.example.challenge.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TicketPriority {
    HIGH("high"),
    LOW("low"),
    NORMAL("normal"),
    URGENT("urgent");

    @JsonValue
    private final String value;

    TicketPriority(String value){
        this.value= value;
    }

    public String getValue() {
        return value;
    }
}
