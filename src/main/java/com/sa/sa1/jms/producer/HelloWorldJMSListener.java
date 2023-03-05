package com.sa.sa1.jms.producer;

import com.sa.sa1.data.HelloResponse;
import com.sa.sa1.service.HelloService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldJMSListener {
    private HelloService helloService;

    public HelloWorldJMSListener(HelloService helloService) {
        this.helloService = helloService;
    }
    @JmsListener(destination = "testQueue")
    public void listenMessage(String name) {
        helloService.hello(name);
    }
}
