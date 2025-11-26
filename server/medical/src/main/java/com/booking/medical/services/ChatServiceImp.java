package com.booking.medical.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.booking.medical.models.dtos.ChatInputDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatServiceImp implements ChatService {
    private String url = "http://localhost:8000"; // url nomal
    // private String url = "http://python-fastapi:8000"; // url docker

    @Override
    public Object SendMessage(ChatInputDTO chatInputDTO) {
        log.info("Run send message to FastAPI");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, String> body = new HashMap<>() {
            {
                put("prompt", chatInputDTO.getMessage());
            }
        };

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url + "/chats", request, Object.class);
    }

}
