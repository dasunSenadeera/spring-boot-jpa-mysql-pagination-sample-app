package com.example.challenge.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TicketType {
    INCIDENT("incident"),
    PROBLEM("problem"),
    QUESTION("question"),
    TASK("task");

    @JsonValue
    private final String value;

    TicketType(String value){
        this.value= value;
    }

    public String getValue() {
        return value;
    }
}
