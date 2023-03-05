package com.sa.sa1.controller;

import com.sa.sa1.data.HelloDetail;
import com.sa.sa1.data.HelloResponse;
import com.sa.sa1.jms.consumer.HelloWorldJMSMessager;
import com.sa.sa1.service.HelloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class HelloWorldController
{
    private HelloService helloService;
    private HelloWorldJMSMessager helloWorldJMSMessager;

    public HelloWorldController(HelloService helloService, HelloWorldJMSMessager helloWorldJMSMessager) {
        this.helloService = helloService;
        this.helloWorldJMSMessager = helloWorldJMSMessager;
    }

    @GetMapping(value="/hello")
    public Mono<HelloResponse> helloworld(){
        return helloService.hello("world");
    }
    
    @PostMapping(value="/hello")
    public Mono<HelloResponse> hello(@RequestParam(name = "name", required = false) String name) {
        return helloService.hello(name);
    }

    @GetMapping(value="/helloList")
    public Flux<HelloDetail> helloList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return helloService.helloList(page, size, null);
    }

    @GetMapping(value="/helloList/{name}")
    public Flux<HelloDetail> helloList(@PathVariable String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return helloService.helloList(page, size, name);
    }

    @PostMapping(value="/hellov1")
    public Mono<HelloResponse> hellov1(@RequestParam(name = "name") String name) {
        return helloService.hellov1(name);
    }
    @PostMapping(value="/hellov2")
    public Mono<HelloResponse> hellov2(@RequestParam(name = "name") String name) {
        return helloService.hellov2(name);
    }

    @PostMapping(value="/hello-jms")
    public void helloJms(@RequestParam(name = "name") String name) {
        helloWorldJMSMessager.sendMessage(name);
    }
}
