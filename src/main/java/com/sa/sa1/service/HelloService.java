package com.sa.sa1.service;

import com.sa.sa1.data.Hello;
import com.sa.sa1.data.HelloDetail;
import com.sa.sa1.data.HelloResponse;
import com.sa.sa1.feignclient.HelloWorldFeignClient;
import com.sa.sa1.jms.consumer.HelloWorldJMSMessager;
import com.sa.sa1.repository.HelloRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HelloService {
    private HelloRepository helloRepository;
    @Autowired
    private HelloResponse helloResponse;
    @Autowired
    private RestTemplate resttTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private HelloWorldFeignClient feignClient;

    private HelloWorldJMSMessager helloWorldJMSMessager;

    public HelloService(HelloRepository helloRepository, HelloWorldJMSMessager helloWorldJMSMessager){
        this.helloRepository = helloRepository;
        this.helloWorldJMSMessager = helloWorldJMSMessager;
    }
    public Mono<HelloResponse> hello(String name){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(name==null) {
            name = "world";
        }
        Hello hello = new Hello(now, name);
        helloRepository.save(hello);
        helloResponse.setData(name);
        return Mono.just(helloResponse);
    }

    public Mono<HelloResponse> helloRestTemplate(String name){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("name", name);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        HelloResponse helloResponse = restTemplate.postForObject("http://localhost:9090/hello",entity,HelloResponse.class);
        return Mono.just(helloResponse);
    }
    public Mono<HelloResponse> helloWebSocket(String name){
        WebClient webClient = WebClient.builder().build();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("name", name);
        Mono<HelloResponse> helloResponse = WebClient.create().post().uri("http://localhost:9090/hello").bodyValue(map).retrieve().bodyToMono(HelloResponse.class);
        return helloResponse;
    }

    @CircuitBreaker(name="helloJms", fallbackMethod = "jmsFallback")
    public void helloJms(String name) {
        helloWorldJMSMessager.sendMessage(name);
    }

    public void jmsFallback(String name, Throwable t) {
        hello("fallback-"+name);
    }

    public Flux<HelloDetail> helloList(int page, int size, String name) {
//        if(name == null) {
//            return Flux.fromIterable(helloRepository.findAll(PageRequest.of(page, size))).map(helllo -> {
//                HelloDetail helloDetail = new HelloDetail();
//                helloDetail.setId(helllo.getId());
//                helloDetail.setDate(helllo.getTimestamp());
//                helloDetail.setName(helllo.getName());
//                return helloDetail;
//            });
//        } else {
//            return Flux.fromIterable(helloRepository.findByName(name,PageRequest.of(page, size))).map(helllo -> {
//                HelloDetail helloDetail = new HelloDetail();
//                helloDetail.setId(helllo.getId());
//                helloDetail.setDate(helllo.getTimestamp());
//                helloDetail.setName(helllo.getName());
//                return helloDetail;
//            });
//        }
        return Flux.fromIterable(helloRepository.findAll(PageRequest.of(page, size))).map(helllo -> {
                HelloDetail helloDetail = new HelloDetail();
                helloDetail.setId(helllo.getId());
                helloDetail.setDate(helllo.getTimestamp());
                helloDetail.setName(helllo.getName());
                return helloDetail;
            }).filter(helloDetail -> name==null ? true : helloDetail.getName().equalsIgnoreCase(name));

    }

    public Mono<HelloResponse> helloDiscoveryClient(String name) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("name", name);

        List<ServiceInstance> instances = discoveryClient.getInstances("helloworld");
        String uri = instances.get(0).getUri().toString() + "/hello";

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        HelloResponse helloResponse = restTemplate.postForObject(uri,entity,HelloResponse.class);
        return Mono.just(helloResponse);

    }

    public Mono<HelloResponse> helloLoadBalancedRestTemplate(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("name", name);

        String uri = "http://helloworld/hello";

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<HelloResponse> helloResponse = resttTemplate.exchange(uri, HttpMethod.POST, entity,HelloResponse.class);
        return Mono.just(helloResponse.getBody());
    }

    public Mono<HelloResponse> helloFeignClient(String name) {
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("name", name);
        return Mono.just(feignClient.postHello(map));
    }
}
