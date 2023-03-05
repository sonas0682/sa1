package com.sa.sa1.configuration;

import com.sa.sa1.beans.HelloBean;
import com.sa.sa1.data.HelloResponse;
import com.sa.sa1.jms.consumer.HelloWorldJMSMessager;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

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

    @Bean
    public HelloWorldJMSMessager helloWorldJMSMessager() {
        return new HelloWorldJMSMessager();
    }

}
