package com.example.ship.serverProxy.Controller;


import com.example.ship.serverProxy.dto.UsersResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/server-proxy")
public class ServerProxyController {

    private final WebClient webClient;


    @Autowired
    public ServerProxyController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> fetchUsers(@PathVariable Long id){
        UsersResponseDTO usersResponseDTO = webClient.get()
                .uri("/users/"+id)
                .retrieve()
                .bodyToMono(UsersResponseDTO.class)
                .toFuture()
                .join();
        System.out.println("Values from Server-Proxy"+usersResponseDTO.toString());
        return ResponseEntity.ok(usersResponseDTO);
    }


}
