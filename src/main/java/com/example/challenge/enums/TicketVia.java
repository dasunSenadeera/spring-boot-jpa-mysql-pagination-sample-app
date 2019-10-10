package com.example.challenge.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TicketVia {
    WEB("web"),
    CHAT("chat"),
    VOICE("voice");

    @JsonValue
    private final String value;

    TicketVia(String value){
        this.value= value;
    }

    public String getValue() {
        return value;
    }
}
