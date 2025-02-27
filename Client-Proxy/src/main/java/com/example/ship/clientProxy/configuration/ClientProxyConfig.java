package com.example.ship.clientProxy.configuration;


import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class ClientProxyConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        HttpClient httpClient= HttpClient.create(ConnectionProvider.create("custom"))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,50000)
                .responseTimeout(Duration.ofSeconds(10))
                .keepAlive(true);

        return builder
                .baseUrl("http://localhost:9081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
