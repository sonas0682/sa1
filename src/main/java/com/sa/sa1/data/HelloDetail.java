package com.sa.sa1.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class HelloDetail {
    private long id;
    private Timestamp date;
    private String name;
}
