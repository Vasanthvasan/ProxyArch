package com.example.ship.clientProxy.Controller;

import com.example.ship.clientProxy.DTO.UserResponseDTO;
import org.apache.catalina.User;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.*;

import static ch.qos.logback.core.joran.sanity.Pair.*;

@RestController
@RequestMapping("/client-proxy")
public class ClientProxy {
    
    private final WebClient webClient;
    private final BlockingQueue<Pair<Long, CompletableFuture<UserResponseDTO>>>  queue = new LinkedBlockingQueue<>();


    @Autowired
    public ClientProxy(WebClient webClient) {
        this.webClient = webClient;
        handleRequestsSequentially();

    }


    @GetMapping("/users/{id}")
    public CompletableFuture<UserResponseDTO> handleClientProxyRequest(@PathVariable Long id){
        CompletableFuture<UserResponseDTO> responseFuture = new CompletableFuture<>();
        queue.add(Pair.of(id, responseFuture));
        System.out.println("Request added to queue. Processing...");
        return responseFuture;
    }


    private void handleRequestsSequentially() {
        new Thread(() -> {
            while (true) {
                try {
                    Pair<Long, CompletableFuture<UserResponseDTO>> request = queue.take();
                    handleRequest(request);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void handleRequest(Pair<Long, CompletableFuture<UserResponseDTO>> request) {
        Long id = request.getLeft();
        CompletableFuture<UserResponseDTO> responseFuture = request.getRight();

        UserResponseDTO response = webClient.get()
                .uri("/server-proxy/" + id)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .block();

        responseFuture.complete(response);
    }

}
