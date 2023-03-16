package com.sa.sa1.controller;

import com.sa.sa1.data.HelloDetail;
import com.sa.sa1.data.HelloResponse;
import com.sa.sa1.jms.consumer.HelloWorldJMSMessager;
import com.sa.sa1.service.HelloService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController
{
    private HelloService helloService;


    public HelloWorldController(HelloService helloService, HelloWorldJMSMessager helloWorldJMSMessager) {
        this.helloService = helloService;
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

    @PostMapping(value="/helloRestTemplate")
    public Mono<HelloResponse> helloRestTemplate(@RequestParam(name = "name") String name) {
        return helloService.helloRestTemplate(name);
    }
    @PostMapping(value="/helloWebSocket")
    public Mono<HelloResponse> helloWebSocket(@RequestParam(name = "name") String name) {
        return helloService.helloWebSocket(name);
    }

    @PostMapping(value="/helloJms")
    public void helloJms(@RequestParam(name = "name") String name) {
        helloService.helloJms(name);
    }

    @PostMapping(value="/helloDiscoveryClient")
    public Mono<HelloResponse> helloDiscoveryClient(@RequestParam(name = "name") String name) {
        return helloService.helloDiscoveryClient(name);
    }

    @PostMapping(value="/helloLoadBalancedRestTemplate")
    public Mono<HelloResponse> helloLoadBalancedRestTemplate(@RequestParam(name = "name") String name) {
        return helloService.helloLoadBalancedRestTemplate(name);
    }

    @PostMapping(value="/helloFeignClient")
    public Mono<HelloResponse> helloFeignClient(@RequestParam(name = "name") String name) {
        return helloService.helloFeignClient(name);
    }

}
