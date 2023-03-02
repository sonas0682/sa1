package com.sa.sa1.configuration;

import com.sa.sa1.beans.HelloBean;
import com.sa.sa1.data.HelloResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfiguration {

    @Bean
    public HelloBean helloBean() {
        return new HelloBean();
    }

    @Bean
    public HelloResponse helloResponse() {
        return new HelloResponse();
    }
}
