package com.sa.sa1.beans;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Data
public class HelloBean {
    @Value("${hello.text}")
    private String txt;
}
