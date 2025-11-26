package com.booking.medical.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.medical.common.Response;
import com.booking.medical.models.dtos.ChatInputDTO;
import com.booking.medical.services.ChatService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class ChatController {
    private ChatService chatService;
    private Response resp = new Response();

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Response> Chat(@RequestBody @Valid ChatInputDTO chatInputDTO) {
        Object result = chatService.SendMessage(chatInputDTO);
        resp.setMessage("Thành công");
        resp.setStatus(HttpStatus.OK);
        resp.put("data", result);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}
