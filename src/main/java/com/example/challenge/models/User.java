package com.example.challenge.models;

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
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    private long _id;

    private String url;

    @JsonProperty("external_id")
    private String externalId;

    private String name;

    private String alias;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss XXX")
    private LocalDateTime created_at;

    private boolean active;

    private boolean verified;

    private boolean shared;

    private Locale locale;

    private TimeZone timezone;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss XXX")
    private LocalDateTime last_login_at;

    @Email
    private String email;

    private String phone;

    private String signature;

    private int organization_id;

    @ElementCollection
    @CollectionTable(name = "user_tags")
    private List<String> tags;

    private boolean suspended;

    private String role;

}


//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss z")
//    private java.sql.Timestamp last_login_at;

//    private String last_login_at
