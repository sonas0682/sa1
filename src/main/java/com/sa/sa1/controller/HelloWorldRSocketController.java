package com.sa.sa1.controller;

import com.sa.sa1.data.HelloResponse;
import com.sa.sa1.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Controller
public class HelloWorldRSocketController {
    @Autowired
    private HelloService helloService;
    @Autowired
    private RSocketRequester rSocketRequester;

    @MessageMapping("greeting")
    public Mono<HelloResponse> hello(Mono<String> name) {
        return name.map(in -> helloService.hello(in).block());
    }

    @GetMapping("/rs/hello/{name}")
    public void rsocketHello(@PathVariable(value="name") String name) {
        rSocketRequester.route("greeting").data(name).retrieveMono(HelloResponse.class).subscribe(out -> System.out.println(out.getData()));
    }
}
