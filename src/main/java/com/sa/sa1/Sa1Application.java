package com.sa.sa1;

import com.netflix.discovery.EurekaNamespace;
import com.sa.sa1.configuration.HelloConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
public class Sa1Application {

	public static void main(String[] args) {
		SpringApplication.run(Sa1Application.class, args);
	}

}
