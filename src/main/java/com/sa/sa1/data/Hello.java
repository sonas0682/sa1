package com.sa.sa1.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor

public class Hello {
    public Hello(Timestamp timestamp, String name) {
        this.timestamp = timestamp;
        this.name = name;
    }
    @Id
    @SequenceGenerator(name="client_generator", sequenceName = "hello_seq", allocationSize=1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    private long id;
    @NonNull
    private Timestamp timestamp;
    @NonNull
    private String name;
}
