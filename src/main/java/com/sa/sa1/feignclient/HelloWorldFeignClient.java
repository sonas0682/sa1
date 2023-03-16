package com.sa.sa1.feignclient;

import com.sa.sa1.configuration.FromUrlEncodedClientConfiguration;
import com.sa.sa1.data.HelloResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name="helloworld", configuration = FromUrlEncodedClientConfiguration.class)
public interface HelloWorldFeignClient {

    @RequestMapping(method = RequestMethod.POST, value="/hello", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    HelloResponse postHello(MultiValueMap<String, String> data);
}
