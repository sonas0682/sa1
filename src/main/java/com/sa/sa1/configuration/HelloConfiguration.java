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
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

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
    @Bean
    public RSocketRequester getRSocketRequester() {
        RSocketStrategies strategies = RSocketStrategies.builder().encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .build();
        strategies = RSocketStrategies.builder().encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();
        RSocketRequester requester = RSocketRequester
                .builder()
                .rsocketConnector(connector -> connector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .rsocketStrategies(strategies)
                .dataMimeType(MimeTypeUtils.ALL)
                //.tcp("localhost",7000)
                .websocket(URI.create("ws://localhost:7000/rsocket"));
        return requester;
    }



}
