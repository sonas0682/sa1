package com.sa.sa1.jms.consumer;

import ch.qos.logback.classic.pattern.MessageConverter;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;

public class HelloWorldJMSMessager {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String name) {
        jmsTemplate.convertAndSend(name);
    }
}
