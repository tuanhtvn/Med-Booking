package com.booking.medical.services;

import com.booking.medical.models.dtos.ChatInputDTO;

public interface ChatService {
    Object SendMessage(ChatInputDTO chatInputDTO);
}
